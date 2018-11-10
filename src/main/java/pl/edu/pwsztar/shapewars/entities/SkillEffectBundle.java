package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="SKILL_EFFECT_BUNDLE")
public class SkillEffectBundle {

    @Id
    @GeneratedValue
    @Column(name="SKILL_EFFECT_BUNDLE_ID")
    private Long ID;

    @Column(name="ACCURACY")
    private Double accuracy;

    @OneToMany
    @JoinColumn(name="SKILL_EFFECT_ID")
    private List<SkillEffect> skillEffects;

}
