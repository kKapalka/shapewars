package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.ColorDamage;

@Data
@Builder
public class ColorDamageDto {
    private Long id;
    private String enemyColorName;
    private Long damagePercentage;

    public static ColorDamageDto fromEntity(ColorDamage entity){
        return ColorDamageDto.builder()
                .id(entity.getID())
                .enemyColorName(entity.getEnemyColor().getColorName())
                .damagePercentage(entity.getDamagePercentage())
                .build();
    }
}
