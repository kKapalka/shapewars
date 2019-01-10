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
    private String shapeName;
    private List<SkillDto> shapeSkillset;
    private String color;
    private int maximumHp;
    private int currentHp;
    private int strength;
    private int armor;
    private int speed;
    private String fighterImage;
    private String slot;

    public static FighterCombatDto fromEntity(Fighter fighter){
        return FighterCombatDto.builder()
                .id(fighter.getID())
                .shapeName(fighter.getShape().getName())
                .shapeSkillset(fighter.getShape().getSkillSet().stream().map(SkillDto::fromEntity).collect(Collectors.toList()))
                .color(fighter.getColor().getColorName())
                .maximumHp(fighter.getHitPoints().intValue())
                .currentHp(fighter.getHitPoints().intValue())
                .strength(fighter.getStrength().intValue())
                .armor(fighter.getArmor().intValue())
                .speed(fighter.getSpeed().intValue())
                .slot(fighter.getSlot().name())
                .fighterImage(new String(fighter.getFighterImage()))
                .build();
    }
}
