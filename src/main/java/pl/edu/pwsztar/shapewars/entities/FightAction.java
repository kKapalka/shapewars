package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="FIGHT_ACTION")
public class FightAction {

    @Id
    @GeneratedValue
    @Column(name="FIGHT_ACTION_ID")
    private Long Id;

    @ManyToOne
    @JoinColumn(name="FIGHT_ID")
    private Fight fight;

    @Column(name="ACTION_TIME")
    private LocalDateTime actionTime;

    @ManyToOne
    @JoinColumn(name="ACTIVE_FIGHTER_ID")
    private Fighter activeFighter;

    @ManyToOne
    @JoinColumn(name="SKILL_ID")
    private Skill skill;

    @ManyToOne
    @JoinColumn(name="SELECTED_TARGET_ID")
    private Fighter selectedTarget;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = SkillEffectResult.class)
    @JoinColumn(name="FIGHT_ACTION_ID", referencedColumnName = "FIGHT_ACTION_ID", nullable = false)
    private List<SkillEffectResult> resultSet;

    @ManyToOne
    @JoinColumn(name="NEXT_ACTIVE_FIGHTER_ID")
    private Fighter nextActiveFighter;

}
