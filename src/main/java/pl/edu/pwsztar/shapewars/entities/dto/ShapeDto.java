package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Shape;
import pl.edu.pwsztar.shapewars.entities.Skill;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class ShapeDto {

    private Long id;
    private String name;
    private List<Long> skillIDset;
    private List<Long> HPParameters;
    private List<Long> STRParameters;
    private List<Long> ARMParameters;
    private Long speed;


    public static ShapeDto fromEntity(Shape shape){
        return ShapeDto.builder()
                .id(shape.getId())
                .name(shape.getName())
                .skillIDset(shape.getSkillSet().stream().map(Skill::getID).collect(Collectors.toList()))
                .HPParameters(Arrays.asList(shape.getBaselineHp(),shape.getHPMinGrowth(),shape.getHPMaxGrowth()))
                .STRParameters(Arrays.asList(shape.getBaselineStrength(),shape.getSTRMinGrowth(),shape.getSTRMaxGrowth()))
                .ARMParameters(Arrays.asList(shape.getBaselineArmor(),shape.getARMMinGrowth(),shape.getARMMaxGrowth()))
                .speed(shape.getBaselineSpeed())
                .build();
    }

}
