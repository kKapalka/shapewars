package pl.edu.pwsztar.shapewars.services;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.FightDto;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.enums.FightStatus;
import pl.edu.pwsztar.shapewars.repositories.FightRepository;

import java.util.Arrays;

@Service
public class FightService {

    @Autowired
    private FightRepository fightRepository;

    @Autowired
    private UserService userService;

    public FightDto save(FightDto dto){
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
            fight.setPlayerOne(userService.getUserById(dto.getPlayerOneId()));
            fight.setPlayerTwo(userService.getUserById(dto.getPlayerTwoId()));
        }
        fight.setFightStatus(FightStatus.valueOf(dto.getFightStatus()));
        return fight;
    }
    public Fight findFightById(Long id){
        return fightRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
