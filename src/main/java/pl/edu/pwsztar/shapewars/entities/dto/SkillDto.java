package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Skill;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class SkillDto {
    private Long id;
    private String name;
    private String icon;
    private String tooltip;
    private Long cost;
    private List<SkillEffectBundleDto> skillEffectBundles;

    public static SkillDto fromEntity(Skill skill){
        return SkillDto.builder().id(skill.getID())
                .name(skill.getName())
                .tooltip(skill.getTooltip())
                .icon(skill.getIcon()==null?"":new String(skill.getIcon()))
                .cost(skill.getCost())
                .skillEffectBundles(skill.getSkillEffectBundles().stream().map(SkillEffectBundleDto::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
