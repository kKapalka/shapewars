package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="SKILL_EFFECT_RESULT")
public class SkillEffectResult {

    @Id
    @GeneratedValue
    @Column(name="SKILL_EFFECT_RESULT_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="TARGET_STATUS_ID")
    private TargetStatus targetStatus;

    @ManyToOne
    @JoinColumn(name="SKILL_EFFECT_ID")
    private SkillEffect skillEffect;

    @Column(name="RESULT")
    private Double result;
}
