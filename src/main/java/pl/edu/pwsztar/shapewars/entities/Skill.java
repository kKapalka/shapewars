package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import pl.edu.pwsztar.shapewars.entities.enums.SkillEffect;
import pl.edu.pwsztar.shapewars.entities.enums.TargetType;
import pl.edu.pwsztar.shapewars.entities.enums.ValueModifierType;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name="EFFECT")
    private SkillEffect skillEffect;

    @Enumerated(EnumType.STRING)
    @Column(name="TARGET_TYPE")
    private TargetType targetType;

    @Column(name="MIN_VALUE")
    private Double minValue;

    @Column(name="MAX_VALUE")
    private Double maxValue;

    @Column(name="ACCURACY")
    private Double accuracy;

    @Enumerated(EnumType.STRING)
    @Column(name="VALUE_MODIFIER_TYPE")
    private ValueModifierType valueModifierType;

    public Skill(){

    }
}
