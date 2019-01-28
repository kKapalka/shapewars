package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="LEARNING_SET_TURN_LOG")
public class LearningSetTurnLog {

    @Id
    @GeneratedValue
    @Column(name="LEARNING_SET_TURN_LOG_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="AGENT_LEARNING_SET_ID")
    private AgentLearningSet agentLearningSet;

    @Column(name="ENEMY_SCORE")
    private Long enemyScore;

    @Column(name="ALLY_SCORE")
    private Long allyScore;
}
