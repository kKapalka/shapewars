package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.ColorMap;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ColorMapDto {

    private Long id;
    private String colorName;
    private String colorMap;
    private List<ColorDamageDto> colorDamageDtoList;

    public static ColorMapDto fromEntity(ColorMap colorMap){
        return ColorMapDto.builder()
                .id(colorMap.getID())
                .colorName(colorMap.getColorName())
                .colorMap(new String(colorMap.getColorMap()))
                .colorDamageDtoList(colorMap.getColorDamageList().stream().map(ColorDamageDto::fromEntity).collect(Collectors.toList()))
                .build();
    }

}
