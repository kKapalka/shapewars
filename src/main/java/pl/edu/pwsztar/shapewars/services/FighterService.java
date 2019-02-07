package pl.edu.pwsztar.shapewars.services;

import static pl.edu.pwsztar.shapewars.utilities.StaticValues.BASIC_EXPERIENCE_VALUE;
import static pl.edu.pwsztar.shapewars.utilities.StaticValues.LEVEL_DIFFERENCE_MODIFIER;

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
import java.util.stream.Collectors;

@Service
public class FighterService {

    @Autowired
    private FighterRepository fighterRepository;

    @Autowired
    private FighterModelService fighterModelService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExperienceThresholdService experienceThresholdService;

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
        return fighterRepository.findCombatantsForUserName(login);
    }
    public List<Fighter> getFightersByLogin(String login){
        return fighterRepository.findAllByOwnerName(login);
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
        Shape shape = fighter.getFighterModelReferrence().getShape();
        long storedStr = 0L;
        long storedHP = 0L;
        long storedARM = 0L;
        for(int i=1;i<fighter.getLevel();i++){
            storedStr+=(long)(new Random().nextInt(shape.getSTRMaxGrowth().intValue() - shape.getSTRMinGrowth().intValue())
                                                                    + shape.getSTRMinGrowth().intValue());
            storedHP+=(long)(new Random().nextInt(shape.getHPMaxGrowth().intValue()-shape.getHPMinGrowth().intValue())
                                                                     +shape.getHPMinGrowth().intValue());
            storedARM+=(long)(new Random().nextInt(shape.getARMMaxGrowth().intValue()-shape.getARMMinGrowth().intValue())
                                                                 +shape.getARMMinGrowth().intValue());

        }
        fighter.setArmorModifier(storedARM);
        fighter.setHitPointsModifier(storedHP);
        fighter.setStrengthModifier(storedStr);
        fighter.setSlot(slot);
        return fighterRepository.save(fighter);
    }

    public void applyLevelChangesToFighters(User winner, User loser){
        winner.getFighterList().forEach(fighter -> {
            if(fighter.getSlot() != FighterSlot.INVENTORY) {
                fighter.setExperiencePoints(Math.round(fighter.getExperiencePoints() + (BASIC_EXPERIENCE_VALUE + (loser.getLevel() * BASIC_EXPERIENCE_VALUE) *
                                                                                       Math.pow(LEVEL_DIFFERENCE_MODIFIER, (winner.getLevel() -
                                                                                                       fighter.getLevel())))));
                Long fighterThreshold = experienceThresholdService.getByLevel(fighter.getLevel()).getThreshold();
                if(fighter.getExperiencePoints() > fighterThreshold) {
                    fighter = levelUp(fighter, fighterThreshold);
                }
                fighterRepository.save(fighter);
            }
        });
        loser.getFighterList().forEach(fighter -> {
            if(fighter.getSlot() != FighterSlot.INVENTORY) {
                fighter.setExperiencePoints((long)Math.round(fighter.getExperiencePoints() + ((float)(BASIC_EXPERIENCE_VALUE + (winner.getLevel() * BASIC_EXPERIENCE_VALUE) *
                                                                                       Math.pow(LEVEL_DIFFERENCE_MODIFIER,
                                                                                             (loser.getLevel() -
                                                                                                       fighter.getLevel())))/2f)));
                Long fighterThreshold = experienceThresholdService.getByLevel(fighter.getLevel()).getThreshold();
                if(fighter.getExperiencePoints() > fighterThreshold) {
                    fighter = levelUp(fighter, fighterThreshold);
                }
                fighterRepository.save(fighter);
            }
        });
    }

    public void clearUnusedFighters(User user){
        List<Fighter> fighters = user.getFighterList();
        fighters.forEach(fighter->fighter.setOwner(null));
        fighterRepository.saveAll(fighters);
    }

    public void tryApplyingLoot(User winner, User loser){
        System.out.println(winner.getLogin());
        System.out.println(loser.getLogin());
        List<Fighter> loot;
        if(loser.getEmail()==null){
            //AI loser can lose shapes from its party - it will be deleted afterwards, so no worry
            loot = loser.getFighterList();
            loot.forEach(fighter->fighter.setOwner(null));
        } else{
            loot = loser.getFighterList().stream().filter(fighter->fighter.getSlot()==FighterSlot.INVENTORY).collect(
                  Collectors.toList());
        }
        System.out.println(loot.stream().map(fighter->fighter.getFighterModelReferrence().getColor().getColorName()+
                                                      " "+fighter.getFighterModelReferrence().getShape().getName()).collect(
              Collectors.toList()));
        //if loser has shapes to lose
        //at least one
        if(loot.size()!=0){
            Fighter fighterToTransfer = loot.get(new Random().nextInt(loot.size()));
            fighterToTransfer.setSlot(FighterSlot.INVENTORY);
            fighterToTransfer.setOwner(winner);
            //at most 2 will be transferred to winner
            if(loot.size()>2){
                fighterToTransfer = loot.get(new Random().nextInt(loot.size()));
                fighterToTransfer.setSlot(FighterSlot.INVENTORY);
                fighterToTransfer.setOwner(winner);
            }
        }
        System.out.println(loot.stream().map(fighter->fighter.getFighterModelReferrence().getColor().getColorName()+
                                                      " "+fighter.getFighterModelReferrence().getShape().getName()+" "+(fighter.getOwner()!=null?fighter.getOwner().getLogin():"null")).collect(
              Collectors.toList()));
        fighterRepository.saveAll(loot);
        //loot.stream().filter(fighter->fighter.getOwner()==null).forEach(fighter-> fighterRepository.delete(fighter));
    }
    private Fighter levelUp(Fighter fighter, Long fighterThreshold){
        fighter.setExperiencePoints(fighter.getExperiencePoints()-fighterThreshold);
        fighter.setLevel(fighter.getLevel()+1);
        Shape shape = fighter.getFighterModelReferrence().getShape();
        fighter.setStrengthModifier(fighter.getStrengthModifier()+
                                    (long)(new Random().nextInt(shape.getSTRMaxGrowth().intValue()-shape.getSTRMinGrowth().intValue())
                                                                +shape.getSTRMinGrowth().intValue()));
        fighter.setHitPointsModifier(fighter.getHitPointsModifier()+
                                     (long)(new Random().nextInt(shape.getHPMaxGrowth().intValue()-shape.getHPMinGrowth().intValue())
                                                                 +shape.getHPMinGrowth().intValue()));
        fighter.setArmorModifier(fighter.getArmorModifier()+
                                 (long)(new Random().nextInt(shape.getARMMaxGrowth().intValue()-shape.getARMMinGrowth().intValue())
                                                             +shape.getARMMinGrowth().intValue()));
        return fighter;
    }
}
