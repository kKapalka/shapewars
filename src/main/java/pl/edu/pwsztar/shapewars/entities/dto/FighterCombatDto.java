package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.Skill;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class FighterCombatDto {
    private Long id;
    private FighterModelReferenceDto fighterModelReferenceDto;
    private List<SkillDto> skillSet;
    private int maximumHp;
    private int currentMana;
    private int maximumMana;
    private int currentHp;
    private int strength;
    private int armor;
    private int speed;
    private String slot;

    public static FighterCombatDto fromEntity(Fighter fighter){
        int currentHp=fighter.getFighterModelReferrence().getShape().getBaselineHp().intValue()+
                fighter.getHitPointsModifier().intValue();
        return FighterCombatDto.builder()
                .id(fighter.getID())
                .fighterModelReferenceDto(FighterModelReferenceDto.fromEntity(fighter.getFighterModelReferrence()))
                .skillSet(fighter.getFighterModelReferrence().getShape().getSkillSet().stream().map(SkillDto::fromEntity).collect(Collectors.toList()))
                .maximumHp(currentHp)
                .currentHp(currentHp)
                .maximumMana(50+(50*fighter.getLevel().intValue()))
                .currentMana(50+(50*fighter.getLevel().intValue()))
                .strength(fighter.getFighterModelReferrence().getShape().getBaselineStrength().intValue()+
                        fighter.getStrengthModifier().intValue())
                .armor(fighter.getFighterModelReferrence().getShape().getBaselineArmor().intValue()+
                        fighter.getArmorModifier().intValue())
                .speed(fighter.getFighterModelReferrence().getShape().getSpeed().intValue())
                .slot(fighter.getSlot().name())
                .build();
    }
}
