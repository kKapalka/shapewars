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
        System.out.println(dto);
        List<Fight> challenges = fightRepository.findChallengeByFightingSides(dto.getPlayerNames().get(0),dto.getPlayerNames().get(1));
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
            fight.setFightingPlayers(userService.findPlayersByLogins(dto.getPlayerNames()));
        }
        if(fight.getFightStatus()==FightStatus.IN_PROGRESS && dto.getWinnerName()!=null){
            User winner = fight.getFightingPlayers().stream()
                    .filter(player->player.getLogin().equals(dto.getWinnerName())).findFirst().get();
            User loser = fight.getFightingPlayers().stream()
                    .filter(player->!player.equals(winner)).findFirst().get();
            userService.applyLevelChangesToUsers(winner,loser);
            fighterService.applyLevelChangesToFighters(winner,loser);
            fighterService.tryApplyingLoot(winner,loser);
        }
        if(fight.getFightStatus()==FightStatus.INVITE_PENDING && dto.getFightStatus().equals("IN_PROGRESS")) {
            List<Fight> challengesToReject = fightRepository.findChallengeByFightingSides(dto.getPlayerNames().get(0),dto.getPlayerNames().get(1));
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
        return fights.stream().distinct().collect(Collectors.toList());
    }

    public Fight findFightInProgressForUser(String login){
        return fightRepository.findFightInProgressForUser(login, PageRequest.of(0,1)).get(0);
    }

    public List<Fight> getChallengesForUser(String login){
        return fightRepository.findChallengesForUser(login);
    }

    public Fight findByChallenger(String login){
        List<Fight> list = fightRepository.findChallengesForUser(login);
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
            fight.setFightingPlayers(Arrays.asList(user,userService.generateOpponentWithLevel(user.getLevel())));
            fight.setFightStatus(FightStatus.IN_PROGRESS);
            return fightRepository.save(fight);
        } else{
            throw new Exception("Cannot initiate fight against bots for user: "+login);
        }
    }
    public void deletePlayer(User user) {
        List<Fight> fights = fightRepository.findByUser(user.getLogin());
        System.out.println(fights);
        if(fights
                 .stream()
                 .filter(fight -> fight.getFightStatus() == FightStatus.IN_PROGRESS)
                 .collect(Collectors.toList())
                 .size() == 0) {
            fights.forEach(fight -> fight.setFightingPlayers(
                  fight.getFightingPlayers().stream().filter(player -> player != user).collect(Collectors.toList())));
            System.out.println(fights.stream().map(Fight::getID).collect(Collectors.toList()));
            fightRepository.saveAll(fights);
            fighterService.clearUnusedFighters(user);
            userService.deleteBot(user);
        }
    }
    /**
     * Metoda automatycznie usuwa
     */
    private void deleteAllIdleBots(){
        List<User> bots = userService.findAllBots();
        bots.forEach(this::deletePlayer);
    }
}
