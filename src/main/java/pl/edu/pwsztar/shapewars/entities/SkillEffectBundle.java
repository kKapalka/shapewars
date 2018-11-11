package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="SKILL_EFFECT_BUNDLE")
@ToString(exclude = "skill")
public class SkillEffectBundle {

    @Id
    @GeneratedValue
    @Column(name="SKILL_EFFECT_BUNDLE_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="SKILL_ID")
    private Skill skill;

    @Column(name="ACCURACY")
    private Double accuracy;

    @OneToMany (mappedBy = "skillEffectBundle")
    private List<SkillEffect> skillEffects;

}
