package pl.edu.pwsztar.shapewars.services;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.enums.Colors;
import pl.edu.pwsztar.shapewars.entities.enums.FighterSlot;
import pl.edu.pwsztar.shapewars.repositories.FighterRepository;

import java.awt.*;

@Service
public class FighterService {

    @Autowired
    private FighterRepository fighterRepository;

    @Autowired
    private ShapeService shapeService;

    @Autowired
    private ColorMapService colorMapService;

    public FighterDto save(FighterDto dto){
        Fighter fighter = updateFighter(dto);
        return FighterDto.fromEntity(fighterRepository.save(fighter));
    }

    public Fighter getFighterById(Long id){
        return fighterRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //Instancje jednostek - edycja/zapis następuje w tych przypadkach:
    // albo są generowane losowo,
    // albo otrzymują level-upy na podstawie odbytej walki
    // albo zmieniają położenie u właściciela
    private Fighter updateFighter(FighterDto dto){
        Fighter fighter = new Fighter();
        if(dto.getId()!=null) {
            fighter = fighterRepository.getOne(dto.getId());
        }
//        } else{
//            fighter = generateFighter();
//        }
        return fighter;
    }

    public Fighter generateFighter(User user){
        Fighter fighter = new Fighter();
        fighter.setShape(shapeService.getRandomShape());
        fighter.setColor(colorMapService.getRandomColor());
        fighter.setOwner(user);
        fighter.setExperiencePoints(0L);
        fighter.setLevel(1L);
        fighter.setArmor(fighter.getShape().getBaselineArmor());
        fighter.setHitPoints(fighter.getShape().getBaselineHp());
        fighter.setStrength(fighter.getShape().getBaselineStrength());
        fighter.setSpeed(fighter.getShape().getBaselineSpeed());
        fighter.setSlot(FighterSlot.INVENTORY);

        return fighterRepository.save(fighter);
    }
}
