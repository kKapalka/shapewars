package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="ACTION")
public class Action {

    @Id
    @GeneratedValue
    @Column(name="ACTION")
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="ACTION_ID", referencedColumnName = "ACTION", nullable = false)
    private List<TargetStatus> targetStatuses;

    @ManyToOne
    @JoinColumn(name="NEXT_ACTIVE_FIGHTER_ID")
    private Fighter nextActiveFighter;

}
