package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import pl.edu.pwsztar.shapewars.entities.enums.SkillStatusEffect;
import pl.edu.pwsztar.shapewars.entities.enums.TargetType;
import pl.edu.pwsztar.shapewars.entities.enums.ValueModifierType;

import javax.persistence.*;

@Data
@Entity
@Table(name="SKILL_EFFECT")
public class SkillEffect {

    @Id
    @GeneratedValue
    @Column(name="SKILL_EFFECT_ID")
    private Long ID;

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
