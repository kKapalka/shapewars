package pl.edu.pwsztar.shapewars.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name="FIGHTLOG")
public class FightLog {

    @Id
    @Generated
    @Column(name="FIGHT_LOG_ID")
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
    @JoinColumn(name="TARGET_ID")
    private Fighter target;

    @ManyToOne
    @JoinColumn(name="SKILL_ID")
    private Skill skill;

    @Column(name="SKILL_RESULT")
    private Double skillResult;

    @ManyToOne
    @JoinColumn(name="NEXT_ACTIVE_FIGHTER_ID")
    private Fighter nextActiveFighter;

}
