package pl.edu.pwsztar.shapewars.services;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.dto.FightDto;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.enums.FightStatus;
import pl.edu.pwsztar.shapewars.repositories.FightRepository;

@Service
public class FightService {

    @Autowired
    private FightRepository fightRepository;

    @Autowired
    private UserService userService;

    public FightDto save(FightDto dto){
        Fight fight = updateFight(dto);
        return FightDto.fromEntity(fightRepository.save(fight));
    }

    private Fight updateFight(FightDto dto){
        Fight fight = new Fight();
        if(dto.getId()!=null){
            fight=fightRepository.getOne(dto.getId());
        }
        fight.setFightStatus(FightStatus.valueOf(dto.getFightStatus()));
        fight.setPlayerOne(userService.getUserById(dto.getPlayerOneId()));
        fight.setPlayerTwo(userService.getUserById(dto.getPlayerTwoId()));
        return fight;
    }
    public Fight findFightById(Long id){
        return fightRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
