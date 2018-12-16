package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.Skill;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class FighterDto {

    private Long id;
    private String shapeName;
    private String image;
    private int level;
    private int xpPoints;
    private List<Long> shapeSkillIDSet;
    private List<String> shapeSkillNameSet;
    private ColorMapDto colorMapDto;
    private int maximumHp;
    private int currentHp;
    private int strength;
    private int armor;
    private int speed;
    private String slot;

    public static FighterDto fromEntity(Fighter fighter){
        return FighterDto.builder()
                .id(fighter.getID())
                .shapeName(fighter.getShape().getName())
                .image(new String(fighter.getShape().getImage()))
                .level(fighter.getLevel().intValue())
                .xpPoints(fighter.getExperiencePoints().intValue())
                .shapeSkillIDSet(fighter.getShape().getSkillSet().stream().map(Skill::getID).collect(Collectors.toList()))
                .shapeSkillNameSet(fighter.getShape().getSkillSet().stream().map(Skill::getName).collect(Collectors.toList()))
                .colorMapDto(ColorMapDto.fromEntity(fighter.getColor()))
                .maximumHp(fighter.getHitPoints().intValue())
                .currentHp(fighter.getHitPoints().intValue())
                .strength(fighter.getStrength().intValue())
                .armor(fighter.getArmor().intValue())
                .speed(fighter.getSpeed().intValue())
                .slot(fighter.getSlot().name())
                .build();
    }
}
