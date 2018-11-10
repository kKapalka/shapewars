package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="TARGET_STATUS")
public class TargetStatus {

    @Id
    @GeneratedValue
    @Column(name="TARGET_STATUS_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="TARGET_ID")
    private Fighter target;

    @OneToMany
    @JoinColumn(name="SKILL_EFFECT_RESULT_ID")
    private List<SkillEffectResult> skillEffectResultList;
}
