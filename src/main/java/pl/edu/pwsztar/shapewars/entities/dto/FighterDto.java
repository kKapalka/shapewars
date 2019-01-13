package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Fighter;

@Builder
@Data
public class FighterDto {

    private Long id;
    private FighterModelReferenceDto fighterModelReferrenceDto;
    private int level;
    private int xpPoints;
    private int maximumHp;
    private int currentHp;
    private int strength;
    private int armor;
    private int speed;
    private String slot;

    public static FighterDto fromEntity(Fighter fighter){
        int currentHp=fighter.getFighterModelReferrence().getShape().getBaselineHp().intValue()+
                fighter.getHitPointsModifier().intValue();
        return FighterDto.builder()
                .id(fighter.getID())
                .fighterModelReferrenceDto(FighterModelReferenceDto.fromEntity(fighter.getFighterModelReferrence()))
                .level(fighter.getLevel().intValue())
                .xpPoints(fighter.getExperiencePoints().intValue())
                .maximumHp(currentHp)
                .currentHp(currentHp)
                .strength(fighter.getFighterModelReferrence().getShape().getBaselineStrength().intValue()+
                        fighter.getStrengthModifier().intValue())
                .armor(fighter.getFighterModelReferrence().getShape().getBaselineArmor().intValue()+
                        fighter.getArmorModifier().intValue())
                .speed(fighter.getFighterModelReferrence().getShape().getSpeed().intValue())
                .slot(fighter.getSlot().name())
                .build();
    }
}
