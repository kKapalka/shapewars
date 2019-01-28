package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.Agent;

@Data
@Builder
public class AgentDto {

    private Long id;
    private String dedicatedPlayerName;
    private Double overallBalancePriority;
    private Double enemyInternalBalancePriority;
    private Double allyInternalBalancePriority;
    private Double individualEnemyPriority;
    private Double individualAllyPriority;
    private Double damageOutputPriority;

    public static AgentDto fromEntity(Agent entity){
        return AgentDto.builder()
                .id(entity.getID())
                .dedicatedPlayerName(entity.getDedicatedPlayer().getLogin())
                .overallBalancePriority(entity.getOverallBalancePriority())
                .enemyInternalBalancePriority(entity.getEnemyInternalBalancePriority())
                .allyInternalBalancePriority(entity.getAllyInternalBalancePriority())
                .individualEnemyPriority(entity.getIndividualEnemyPriority())
                .individualAllyPriority(entity.getIndividualAllyPriority())
                .damageOutputPriority(entity.getDamageOutputPriority())
                .build();
    }
}
