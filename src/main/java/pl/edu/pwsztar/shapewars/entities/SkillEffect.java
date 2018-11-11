package pl.edu.pwsztar.shapewars.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import pl.edu.pwsztar.shapewars.entities.enums.SkillStatusEffect;
import pl.edu.pwsztar.shapewars.entities.enums.TargetType;
import pl.edu.pwsztar.shapewars.entities.enums.ValueModifierType;

import javax.persistence.*;

@Data
@Entity
@Table(name="SKILL_EFFECT")
@ToString(exclude = "skillEffectBundle")
public class SkillEffect {

    @Id
    @GeneratedValue
    @Column(name="SKILL_EFFECT_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="SKILL_EFFECT_BUNDLE_ID")
    private SkillEffectBundle skillEffectBundle;

    @Enumerated(EnumType.STRING)
    @Column(name="EFFECT")
    private SkillStatusEffect skillStatusEffect;

    @Enumerated(EnumType.STRING)
    @Column(name="TARGET_TYPE")
    private TargetType targetType;

    @Column(name="MIN_VALUE")
    private Double minValue;

    @Column(name="MAX_VALUE")
    private Double maxValue;

    @Enumerated(EnumType.STRING)
    @Column(name="VALUE_MODIFIER_TYPE")
    private ValueModifierType valueModifierType;
}
