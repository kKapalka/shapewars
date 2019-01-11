package pl.edu.pwsztar.shapewars.entities.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Action;

@Data
@Builder
public class ActionDto {
    public Long id;
    public Long fightId;
    public String actionTime;
    public Long activeFighterId;
    public Long selectedTargetId;
    public Long nextActiveFighterId;
    public List<TargetStatusDto> targetStatusDtoList;
    public Long skillId;

    public static ActionDto fromEntity(Action action){
        return ActionDto.builder()
                .id(action.getId())
                .fightId(action.getFight().getID())
                        .actionTime(action.getActionTime().toString())
                        .activeFighterId(action.getActiveFighter().getID())
                        .selectedTargetId(action.getSelectedTarget().getID())
                        .nextActiveFighterId(action.getNextActiveFighter()!=null?action.getNextActiveFighter().getID():null)
                        .targetStatusDtoList(action.getTargetStatuses().stream().map(TargetStatusDto::fromEntity).collect(
                              Collectors.toList()))
                        .skillId(action.getSkill()!=null?action.getSkill().getID():null)
                .build();
    }
}
