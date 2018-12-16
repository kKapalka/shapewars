package pl.edu.pwsztar.shapewars.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;
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
    @Column(name="ACTION_ID")
    private Long ID;

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

    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL)
    private List<TargetStatus> targetStatuses;

    @ManyToOne
    @JoinColumn(name="NEXT_ACTIVE_FIGHTER_ID")
    private Fighter nextActiveFighter;

}
