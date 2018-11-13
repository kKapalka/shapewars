package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    @Column(name="TOOLTIP", columnDefinition="TEXT")
    private String tooltip;

    @Column(name="COST")
    private Long cost;

    @OneToMany (mappedBy = "skill", cascade = CascadeType.ALL)
    private List<SkillEffectBundle> skillEffectBundles;

    public Skill(){

    }
}
