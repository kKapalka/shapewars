package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.SkillEffect;

@Data
@Builder
public class SkillEffectDto {

    private Long id;
    private String skillStatusEffect;
    private String targetType;
    private double minValue;
    private double maxValue;
    private String valueModifierType;

    public static SkillEffectDto fromEntity(SkillEffect skillEffect){
        return SkillEffectDto.builder()
                .id(skillEffect.getID())
                .skillStatusEffect(skillEffect.getSkillStatusEffect().name())
                .targetType(skillEffect.getTargetType().name())
                .minValue(skillEffect.getMinValue())
                .maxValue(skillEffect.getMaxValue())
                .valueModifierType(skillEffect.getValueModifierType().name())
                .build();
    }
}
