package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.TurnOrder;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.FightCombatDto;
import pl.edu.pwsztar.shapewars.entities.dto.FightDto;
import pl.edu.pwsztar.shapewars.entities.dto.FighterSpeedDto;
import pl.edu.pwsztar.shapewars.entities.enums.FightStatus;
import pl.edu.pwsztar.shapewars.repositories.FightRepository;
import pl.edu.pwsztar.shapewars.repositories.TurnOrderRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FightService {

    @Autowired
    private FightRepository fightRepository;

    @Autowired
    private TurnOrderRepository turnOrderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FighterService fighterService;

    public FightDto save(FightDto dto) throws Exception {
        List<Fight> challenges = fightRepository.findChallengeByFightingSides(dto.getPlayerOne(),dto.getPlayerTwo());
        if((challenges.size()!=0)  && dto.getId()==null){
            throw new Exception("You cannot challenge the same player twice!");
        }
        Fight fight = updateFight(dto);
        FightDto newDto = FightDto.fromEntity(fightRepository.save(fight));
        deleteAllIdleBots();
        return newDto;
    }

    private Fight updateFight(FightDto dto){
        Fight fight = new Fight();
        if(dto.getId()!=null){
            fight=fightRepository.getOne(dto.getId());
        } else{
            //only new fights can get their player set, because why would other option make sense?
            fight.setPlayerOne(userService.getUserByLogin(dto.getPlayerOne()));
            fight.setPlayerTwo(userService.getUserByLogin(dto.getPlayerTwo()));
        }
        if(fight.getFightStatus()==FightStatus.IN_PROGRESS && Arrays.asList("VICTORY_PLAYER_ONE",
              "VICTORY_PLAYER_TWO").contains(dto.getFightStatus())){
            User winner, loser;
            if(dto.getFightStatus().equals(FightStatus.VICTORY_PLAYER_ONE.name())){
                winner=fight.getPlayerOne();
                loser=fight.getPlayerTwo();
            } else{
                winner=fight.getPlayerTwo();
                loser=fight.getPlayerOne();
            }
            userService.applyLevelChangesToUsers(winner,loser);
            fighterService.applyLevelChangesToFighters(winner,loser);
            fighterService.tryApplyingLoot(winner,loser);
            fight.setPlayerTwo(fight.getPlayerTwo().getEmail()!=null?fight.getPlayerTwo():null);
        }
        if(dto.getFightStatus().equals("ABANDONED") && fight.getPlayerTwo().getEmail()==null){
            fighterService.clearUnusedBotFighters(fight.getPlayerTwo());
        }
        if(fight.getFightStatus()==FightStatus.INVITE_PENDING && dto.getFightStatus().equals("IN_PROGRESS")) {
            List<Fight> challengesToReject = fightRepository.findAllPendingInvitesForPlayers(Arrays.asList(dto.getPlayerOne(),dto.getPlayerTwo()));
            challengesToReject.remove(fight);
            if(challengesToReject.size()>0) {
                fightRepository.updateFightsSetAsAbandoned(challengesToReject.stream().map(Fight::getID).collect(Collectors.toList()));
            }
        }
        if(fight.getFightStatus()!=FightStatus.INVITE_REJECTED) {
            fight.setFightStatus(FightStatus.valueOf(dto.getFightStatus()));
        }

        return fight;
    }
    public Fight findFightById(Long id){
        return fightRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Fight> findByUser(String login){
        List<Fight> fights = fightRepository.findByUser(login);
        fights.addAll(fightRepository.findBotFightsByUser(login));
        return fights.stream().distinct().collect(Collectors.toList());
    }

    public Fight findFightInProgressForUser(String login){
        return fightRepository.findFightInProgressForUser(login, PageRequest.of(0,1)).get(0);
    }

    public List<Fight> getChallengesForUser(String login){
        return fightRepository.findChallengesForUser(login);
    }
    public Fight findByChallenger(String login){
        List<Fight> list = fightRepository.findByChallenger(login);
        return list.size()>0?list.get(0):null;
    }

    public List<TurnOrder> getTurnOrderForFight(FightCombatDto dto, Long turn){
        List<TurnOrder> turnOrders = turnOrderRepository.getTurnOrderForFightByFightIdAndTurnNumber(
                dto.getFightId(), turn);
        if(turnOrders.size()!=8){
            turnOrders = createTurnOrders(dto,turn);
        }
        return turnOrders;
    }

    private List<TurnOrder> createTurnOrders(FightCombatDto dto, Long turn){
        List<FighterSpeedDto> speeds = dto.getFighterSpeedDtos();
        Fight fight = fightRepository.findById(dto.getFightId()).orElseThrow(EntityNotFoundException::new);
        speeds.sort((a, b)->(int)(b.getSpeed()-a.getSpeed()));
        List<TurnOrder> turnOrders = speeds.stream().map(speed->{
            TurnOrder turnOrder = new TurnOrder();
            turnOrder.setFight(fight);
            turnOrder.setTurn(turn);
            turnOrder.setFighter(fighterService.getFighterById(speed.getFighterId()));
            turnOrder.setOrder((long)speeds.indexOf(speed));
            return turnOrder;
        }).collect(Collectors.toList());
        return turnOrderRepository.saveAll(turnOrders);
    }

    public Fight generateBotFightForUser(String login) throws Exception {
        User user = userService.getUserByLogin(login);
        if(fightRepository.findFightInProgressForUser(login,PageRequest.of(0,1)).size()==0){
            Fight fight = new Fight();
            fight.setPlayerOne(user);
            fight.setPlayerTwo(userService.generateOpponentWithLevel(user.getLevel()));
            fight.setFightStatus(FightStatus.IN_PROGRESS);
            return fightRepository.save(fight);
        } else{
            throw new Exception("Cannot initiate fight against bots for user: "+login);
        }
    }
    private void deleteAllIdleBots(){
        List<User> bots = userService.findAllBots();
        bots.forEach(bot->{
            List<Fight> activeFights = fightRepository.findFightInProgressForUser(bot.getLogin(),PageRequest.of(0,1));
            if(activeFights.size()==0){
                fighterService.clearUnusedBotFighters(bot);
                userService.delete(bot);
            }
        });
    }
}
