package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Fighter;

@Data
@Builder
public class FighterBaseDto {

    private Long id;
    private String name;
    private String color;
    private Long level;
    private Long xpPoints;
    private String slot;

    public static FighterBaseDto fromEntity(Fighter entity){
        return FighterBaseDto.builder()
                .id(entity.getID())
                .name(entity.getShape().getName())
                .color(entity.getColor().getColorName())
                .level(entity.getLevel())
                .xpPoints(entity.getExperiencePoints())
                .slot(entity.getSlot().name()).build();
    }
}
