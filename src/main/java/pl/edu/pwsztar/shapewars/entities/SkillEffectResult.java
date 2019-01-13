package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.enums.SkillStatusEffect;
import pl.edu.pwsztar.shapewars.entities.enums.ValueModifierType;

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
    @JoinColumn(name="TARGET_ID")
    private Fighter target;

    @Enumerated(EnumType.STRING)
    @Column(name="EFFECT")
    private SkillStatusEffect skillStatusEffect;

    @Enumerated(EnumType.STRING)
    @Column(name="VALUE_MODIFIER_TYPE")
    private ValueModifierType valueModifierType;

    @Column(name="RESULT")
    private Double result;
}
