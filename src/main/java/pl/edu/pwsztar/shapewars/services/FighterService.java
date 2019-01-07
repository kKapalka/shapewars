package pl.edu.pwsztar.shapewars.services;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.enums.FighterSlot;
import pl.edu.pwsztar.shapewars.repositories.FighterRepository;
import pl.edu.pwsztar.shapewars.repositories.UserRepository;
import pl.edu.pwsztar.shapewars.utilities.FighterImageGenerator;

import java.util.List;


@Service
public class FighterService {

    @Autowired
    private FighterRepository fighterRepository;

    @Autowired
    private ShapeService shapeService;

    @Autowired
    private ColorMapService colorMapService;

    @Autowired
    private UserRepository userRepository;

    //for fighter generation is totally different method
    public FighterDto save(FighterDto dto){
        if(dto.getId()!=null) {
            Fighter fighter = updateFighter(dto);
            return FighterDto.fromEntity(fighterRepository.save(fighter));
        } else{
            return dto;
        }
    }

    public Fighter getFighterById(Long id){
        return fighterRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    public List<Fighter> getFightersByLogin(String login){
        User owner = userRepository.findByLoginEquals(login).orElseThrow(EntityNotFoundException::new);
        return fighterRepository.findAllByOwner(owner);
    }
    //Instancje jednostek - edycja następuje w tych przypadkach:
    // otrzymują level-upy na podstawie odbytej walki
    // zmieniają położenie u właściciela
    private Fighter updateFighter(FighterDto dto){
        Fighter fighter = fighterRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        fighter.setSlot(FighterSlot.valueOf(dto.getSlot()));
        return fighter;
    }

    public Fighter saveFighter(Fighter fighter){
        return fighterRepository.save(fighter);
    }

    public Fighter generateFighter(User user){
        Fighter fighter = new Fighter();
        fighter.setShape(shapeService.getRandomShape());
        fighter.setColor(colorMapService.getRandomColor());
        fighter.setOwner(user);
        fighter.setExperiencePoints(0L);
        fighter.setLevel(user.getLevel());
        fighter.setArmor(fighter.getShape().getBaselineArmor());
        fighter.setHitPoints(fighter.getShape().getBaselineHp());
        fighter.setStrength(fighter.getShape().getBaselineStrength());
        fighter.setSpeed(fighter.getShape().getBaselineSpeed());
        fighter.setSlot(FighterSlot.INVENTORY);
        fighter.setFighterImage(FighterImageGenerator.generateImageFrom(fighter.getShape(),fighter.getColor()));
        return fighterRepository.save(fighter);
    }
    public void refreshFightersViaShape(Shape shape){
        List<Fighter> fighterList = fighterRepository.findAllByShape(shape);
        fighterList.forEach(fighter->{
            fighter.setFighterImage(FighterImageGenerator.generateImageFrom(fighter.getShape(),fighter.getColor()));
            fighterRepository.save(fighter);
        })
    }
    public void refreshFightersViaColorMap(ColorMap colorMap){
        List<Fighter> fighterList = fighterRepository.findAllByColor(colorMap);
        fighterList.forEach(fighter->{
            fighter.setFighterImage(FighterImageGenerator.generateImageFrom(fighter.getShape(),fighter.getColor()));
            fighterRepository.save(fighter);
        })
    }
}
