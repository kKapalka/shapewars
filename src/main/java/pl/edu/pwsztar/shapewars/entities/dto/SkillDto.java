package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Skill;

@Builder
@Data
public class SkillDto {
    private Long id;
    private String name;
    private String tooltip;
    private long cost;
    private String skillEffect;
    private String targetType;
    private double minValue;
    private double maxValue;
    private double accuracy;
    private String valueModifierType;

    public static SkillDto fromEntity(Skill skill){
        return SkillDto.builder().id(skill.getID())
                .name(skill.getName())
                .tooltip(skill.getTooltip())
                .cost(skill.getCost())
                .skillEffect(skill.getSkillEffect().name())
                .targetType(skill.getTargetType().name())
                .minValue(skill.getMinValue())
                .maxValue(skill.getMaxValue())
                .accuracy(skill.getAccuracy())
                .valueModifierType(skill.getValueModifierType().name())
                .build();
    }
}
