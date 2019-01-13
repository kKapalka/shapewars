package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.SkillEffectResult;

@Data
@Builder
public class SkillEffectResultDto {

    private Long id;
    private Long targetId;
    private String statusEffect;
    private String modifierType;
    private double result;

    public static SkillEffectResultDto fromEntity(SkillEffectResult result){
        return SkillEffectResultDto.builder()
              .id(result.getID())
                .targetId(result.getTarget().getID())
              .statusEffect(result.getSkillStatusEffect().name())
                .modifierType(result.getValueModifierType().name())
              .result(result.getResult())
              .build();
    }
}
