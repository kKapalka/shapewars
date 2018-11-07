package pl.edu.pwsztar.shapewars.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name="SHAPE")
public class Shape {
    @Id
    @Generated
    @Column(name="SHAPE_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @ManyToMany
    @JoinTable(name = "SHAPE_SKILL",
            joinColumns = @JoinColumn(name = "SHAPE_ID"),
            inverseJoinColumns = @JoinColumn(name = "SKILL_ID")
    )
    private List<Skill> skillSet;

    @ManyToOne
    @JoinColumn(name="SPECIAL_ABILITY")
    private Skill specialAbility;

    @Column(name="BASELINE_HP")
    private Long baselineHp;

    @Column(name="BASELINE_DMG")
    private Long baselineDamage;

    @Column(name="BASELINE_SPEED")
    private Long baselineSpeed;

    @Column(name="HP_MIN_GROWTH")
    private Long HPMinGrowth;

    @Column(name="HP_MAX_GROWTH")
    private Long HPMaxGrowth;

    @Column(name="DMG_MIN_GROWTH")
    private Long DMGMinGrowth;

    @Column(name="DMG_MAX_GROWTH")
    private Long DMGMaxGrowth;

}
