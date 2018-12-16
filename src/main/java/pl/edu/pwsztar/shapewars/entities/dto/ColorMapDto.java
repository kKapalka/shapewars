package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.ColorMap;

@Data
@Builder
public class ColorMapDto {

    private Long id;
    private String colorName;
    private String colorMap;

    public static ColorMapDto fromEntity(ColorMap colorMap){
        return ColorMapDto.builder()
                .id(colorMap.getID())
                .colorName(colorMap.getColorName().name())
                .colorMap(new String(colorMap.getColorMap()))
                .build();
    }

}
