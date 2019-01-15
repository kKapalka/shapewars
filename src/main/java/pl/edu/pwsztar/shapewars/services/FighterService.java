package pl.edu.pwsztar.shapewars.services;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.Shape;
import pl.edu.pwsztar.shapewars.entities.ColorMap;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.enums.FighterSlot;
import pl.edu.pwsztar.shapewars.repositories.FighterRepository;
import pl.edu.pwsztar.shapewars.repositories.UserRepository;
import pl.edu.pwsztar.shapewars.services.ShapeService;
import pl.edu.pwsztar.shapewars.services.ColorMapService;
import pl.edu.pwsztar.shapewars.utilities.FighterImageGenerator;

import java.util.List;
import java.util.Random;

@Service
public class FighterService {

    @Autowired
    private FighterRepository fighterRepository;

    @Autowired
    private FighterModelService fighterModelService;

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
    public List<Fighter> getCombatantsByLogin(String login){
        User owner = userRepository.findByLoginEquals(login).orElseThrow(EntityNotFoundException::new);
        return fighterRepository.findCombatantsForUser(owner);
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

    public Fighter generateFighter(User user, FighterSlot slot){
        Fighter fighter = new Fighter();
        fighter.setFighterModelReferrence(fighterModelService.getRandomReference());
        fighter.setOwner(user);
        fighter.setExperiencePoints(0L);
        fighter.setLevel(user.getLevel());
        fighter.setArmorModifier(0L);
        fighter.setHitPointsModifier(0L);
        fighter.setStrengthModifier(0L);
        Shape shape = fighter.getFighterModelReferrence().getShape();
        for(int i=1;i<fighter.getLevel();i++){
            fighter.setStrengthModifier(fighter.getStrengthModifier()+
                                        (long)(new Random().nextInt((shape.getSTRMaxGrowth().intValue() - shape.getSTRMinGrowth().intValue())
                                                                    + shape.getSTRMinGrowth().intValue())));
            fighter.setHitPointsModifier(fighter.getHitPointsModifier()+
                                         (long)(new Random().nextInt((shape.getHPMaxGrowth().intValue()-shape.getHPMinGrowth().intValue())
                                                                     +shape.getHPMinGrowth().intValue())));
            fighter.setArmorModifier(fighter.getArmorModifier()+
                                     (long)(new Random().nextInt((shape.getARMMaxGrowth().intValue()-shape.getARMMinGrowth().intValue())
                                                                 +shape.getARMMinGrowth().intValue())));
        }
        fighter.setSlot(slot);
        return fighterRepository.save(fighter);
    }
    public void resetFighterList(User user){
        fighterRepository.deleteAllByOwner(user);
    }
}
