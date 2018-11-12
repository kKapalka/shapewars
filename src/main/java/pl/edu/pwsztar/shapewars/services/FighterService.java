package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.enums.Colors;
import pl.edu.pwsztar.shapewars.entities.enums.FighterSlot;
import pl.edu.pwsztar.shapewars.repositories.FighterRepository;

@Service
public class FighterService {

    @Autowired
    private FighterRepository fighterRepository;

    @Autowired
    private ShapeService shapeService;

    public FighterDto save(FighterDto dto){
        Fighter fighter = updateFighter(dto);
        return FighterDto.fromEntity(fighterRepository.save(fighter));
    }

    //Instancje jednostek - albo są generowane losowo,
    // albo otrzymują level-upy na podstawie odbytej walki
    private Fighter updateFighter(FighterDto dto){
        Fighter fighter = new Fighter();
        if(dto.getId()!=null){
            fighter=fighterRepository.getOne(dto.getId());
        } else{
            fighter = generateFighter();
        }
        return fighter;
    }

    private Fighter generateFighter(){
        Fighter fighter = new Fighter();
        fighter.setShape(shapeService.getRandomShape());
        fighter.setColor(Colors.ColorType.getRandom());
        fighter.setExperiencePoints(0L);
        fighter.setLevel(1L);
        fighter.setArmor(fighter.getShape().getBaselineArmor());
        fighter.setHitPoints(fighter.getShape().getBaselineHp());
        fighter.setStrength(fighter.getShape().getBaselineStrength());
        fighter.setSpeed(fighter.getShape().getBaselineSpeed());
        fighter.setSlot(FighterSlot.INVENTORY);
        return fighter;
    }
}
