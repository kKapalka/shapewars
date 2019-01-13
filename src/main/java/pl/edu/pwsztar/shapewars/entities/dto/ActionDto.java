package pl.edu.pwsztar.shapewars.entities.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.FightAction;

@Data
@Builder
public class ActionDto {
    public Long id;
    public Long fightId;
    public String actionTime;
    public Long activeFighterId;
    public Long selectedTargetId;
    public Long nextActiveFighterId;
    public List<SkillEffectResultDto> skillEffectResultDtoList;
    public Long skillId;

    public static ActionDto fromEntity(FightAction fightAction){
        return ActionDto.builder()
                .id(fightAction.getId())
                .fightId(fightAction.getFight().getID())
                        .actionTime(fightAction.getActionTime().toString())
                        .activeFighterId(fightAction.getActiveFighter().getID())
                        .selectedTargetId(fightAction.getSelectedTarget().getID())
                        .nextActiveFighterId(fightAction.getNextActiveFighter()!=null? fightAction.getNextActiveFighter().getID():null)
                        .skillEffectResultDtoList(fightAction.getResultSet().stream().map(SkillEffectResultDto::fromEntity).collect(
                              Collectors.toList()))
                        .skillId(fightAction.getSkill()!=null? fightAction.getSkill().getID():null)
                .build();
    }
}
