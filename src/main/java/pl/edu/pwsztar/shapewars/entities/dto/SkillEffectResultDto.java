package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.SkillEffectResult;

@Data
@Builder
public class SkillEffectResultDto {

    private Long id;
    private SkillEffectDto skillEffect;
    private double result;

    public static SkillEffectResultDto fromEntity(SkillEffectResult result){
        return SkillEffectResultDto.builder()
              .id(result.getID())
              .skillEffect(SkillEffectDto.fromEntity(result.getSkillEffect()))
              .result(result.getResult())
              .build();
    }
}
