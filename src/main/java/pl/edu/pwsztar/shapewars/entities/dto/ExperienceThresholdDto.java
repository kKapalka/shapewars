package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.ExperienceThreshold;

@Data
@Builder
public class ExperienceThresholdDto {

    private Long id;
    private Long level;
    private Long threshold;

    public static ExperienceThresholdDto fromEntity(ExperienceThreshold entity){
        return ExperienceThresholdDto.builder()
                .id(entity.getID())
                .level(entity.getLevel())
                .threshold(entity.getThreshold()).build();
    }
}
