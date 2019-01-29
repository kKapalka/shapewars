package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="AGENT_LEARNING_SET")
public class AgentLearningSet {

    @Id
    @GeneratedValue
    @Column(name="AGENT_LEARNING_SET_ID")
    private Long ID;
    
    @ManyToOne
    @JoinColumn(name="AGENT_ID")
    private Agent agent;

    @ManyToOne
    @JoinColumn(name="FIGHT_ID")
    private Fight fight;

    @Column(name="AGENT_VERSION")
    private Long agentVersion;

    @Column(name="LEGACY_OVERALL_BALANCE_PRIORITY")
    private Double overallBalancePriority;

    @Column(name="LEGACY_ENEMY_INTERNAL_BALANCE_PRIORITY")
    private Double enemyInternalBalancePriority;

    @Column(name="LEGACY_ALLY_INTERNAL_BALANCE_PRIORITY")
    private Double allyInternalBalancePriority;

    @Column(name="LEGACY_INDIVIDUAL_ENEMY_PRIORITY")
    private Double individualEnemyPriority;

    @Column(name="LEGACY_INDIVIDUAL_ALLY_PRIORITY")
    private Double individualAllyPriority;
    
    @Column(name="LEGACY_DAMAGE_OUTPUT_PRIORITY")
    private Double damageOutputPriority;

    @OneToMany(mappedBy = "agentLearningSet")
    private List<LearningSetTurnLog> learningSetTurnLogList;
}
