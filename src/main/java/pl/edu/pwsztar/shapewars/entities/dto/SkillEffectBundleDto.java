package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class SkillEffectBundleDto {

    private Long id;
    private double accuracy;
    private List<SkillEffectDto> skillEffectDtos;

    public static SkillEffectBundleDto fromEntity(SkillEffectBundle skillEffectBundle){
        return SkillEffectBundleDto.builder()
                .id(skillEffectBundle.getID())
                .accuracy(skillEffectBundle.getAccuracy())
                .skillEffectDtos(skillEffectBundle.getSkillEffects().stream().map(SkillEffectDto::fromEntity).collect(Collectors.toList()))
                .build();
    }

}
