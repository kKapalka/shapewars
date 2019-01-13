package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.FighterModelReference;
import pl.edu.pwsztar.shapewars.entities.Skill;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class FighterModelReferenceDto {

    private Long id;
    private String shapeName;
    private String colorName;
    private List<Long> skillSet;
    private String model;

    public static FighterModelReferenceDto fromEntity(FighterModelReference entity){
        return FighterModelReferenceDto.builder()
                .id(entity.getID())
                .shapeName(entity.getShape().getName())
                .colorName(entity.getColor().getColorName())
                .skillSet(entity.getShape().getSkillSet().stream().map(Skill::getID).collect(Collectors.toList()))
                .model(new String(entity.getFighterImage())).build();
    }
}
