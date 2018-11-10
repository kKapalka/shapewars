package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.enums.SkillStatusEffect;
import pl.edu.pwsztar.shapewars.entities.enums.TargetType;
import pl.edu.pwsztar.shapewars.entities.enums.ValueModifierType;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="SKILL")
public class Skill {
    @Id
    @GeneratedValue
    @Column(name="SKILL_ID")
    private Long ID;

    @Column(name="NAME")
    private String name;

    @Column(name="TOOLTIP")
    private String tooltip;

    @Column(name="COST")
    private Long cost;

    @OneToMany
    @JoinColumn(name="SKILL_EFFECT_BUNDLE_ID")
    private List<SkillEffectBundle> skillEffectBundles;

    public Skill(){

    }
}
