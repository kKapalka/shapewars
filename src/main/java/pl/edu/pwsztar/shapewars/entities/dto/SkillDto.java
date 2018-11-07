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

    public static SkillDto fromEntity(Skill skill){
        return SkillDto.builder().id(skill.getID())
                .name(skill.getName())
                .tooltip(skill.getTooltip())
                .build();
    }
}
