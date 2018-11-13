package pl.edu.pwsztar.shapewars.entities.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.TargetStatus;

@Data
@Builder
public class TargetStatusDto {

    private Long id;
    private Long targetId;
    private List<SkillEffectResultDto> resultDtoList;

    public static TargetStatusDto fromEntity(TargetStatus targetStatus){
        return TargetStatusDto.builder()
                              .id(targetStatus.getID())
                              .targetId(targetStatus.getTarget().getID())
                              .resultDtoList(targetStatus.getSkillEffectResultList()
                                                         .stream().map(SkillEffectResultDto::fromEntity).collect(
                                    Collectors.toList()))
                              .build();
    }
}
