package pl.edu.pwsztar.shapewars.services;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.FightDto;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.enums.FightStatus;
import pl.edu.pwsztar.shapewars.repositories.FightRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class FightService {

    @Autowired
    private FightRepository fightRepository;

    @Autowired
    private UserService userService;

    public FightDto save(FightDto dto) throws Exception {
        List<Fight> challenges = fightRepository.findChallengeByFightingSides(userService.getUserByLogin(dto.getPlayerOne()),
                userService.getUserByLogin(dto.getPlayerTwo()));
        if((challenges.size()!=0)  && dto.getId()==null){
            throw new Exception("You cannot challenge the same player twice!");
        }
        Fight fight = updateFight(dto);
        FightDto newDto = FightDto.fromEntity(fightRepository.save(fight));
        if(Arrays.asList(FightStatus.VICTORY_PLAYER_ONE,FightStatus.VICTORY_PLAYER_TWO)
                .contains(fight.getFightStatus())){
            userService.processFightFinalization(fight);
        }
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
        fight.setFightStatus(FightStatus.valueOf(dto.getFightStatus()));
        return fight;
    }
    public Fight findFightById(Long id){
        return fightRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Fight> findByUser(String login){
        return fightRepository.findByUser(userService.getUserByLogin(login));
    }

    public List<Fight> getChallengesForUser(String login){
        return fightRepository.findChallengesForUser(userService.getUserByLogin(login));
    }
    public Fight findByChallenger(String login){
        List<Fight> list = fightRepository.findByChallenger(userService.getUserByLogin(login), PageRequest.of(0,1));
        return list.size()>0?list.get(0):null;
    }
}
