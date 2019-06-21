package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="SHAPE")
public class Shape {
    @Id
    @GeneratedValue
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

    @Column(name="BASELINE_HP")
    private Long baselineHp;

    @Column(name="BASELINE_STR")
    private Long baselineStrength;

    @Column(name="BASELINE_ARM")
    private Long baselineArmor;

    @Column(name="SPEED")
    private Long speed;

    @Column(name="HP_MIN_GROWTH")
    private Long HPMinGrowth;

    @Column(name="HP_MAX_GROWTH")
    private Long HPMaxGrowth;

    @Column(name="STR_MIN_GROWTH")
    private Long STRMinGrowth;

    @Column(name="STR_MAX_GROWTH")
    private Long STRMaxGrowth;

    @Column(name="ARM_MIN_GROWTH")
    private Long ARMMinGrowth;

    @Column(name="ARM_MAX_GROWTH")
    private Long ARMMaxGrowth;

    @Column(name="IMAGE")
    private byte[] image;
}
