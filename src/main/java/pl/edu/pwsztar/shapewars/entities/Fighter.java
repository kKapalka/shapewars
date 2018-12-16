package pl.edu.pwsztar.shapewars.entities;

import lombok.*;
import pl.edu.pwsztar.shapewars.entities.enums.Colors;
import pl.edu.pwsztar.shapewars.entities.enums.FighterSlot;

import javax.persistence.*;

@Data
@Entity
@Table(name="FIGHTER")
@NoArgsConstructor
@ToString(exclude="owner")
public class Fighter {

    @Id
    @GeneratedValue
    @Column(name="FIGHTER_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="SHAPE_ID")
    private Shape shape;

    @ManyToOne
    @JoinColumn(name="OWNER_ID")
    private User owner;

    @Column(name="LEVEL")
    private Long level;

    @Column(name="XP_POINTS")
    private Long experiencePoints;

    @ManyToOne
    @JoinColumn(name="COLOR_ID")
    private ColorMap color;

    @Column(name="HITPOINTS")
    private Long hitPoints;

    @Column(name="STRENGTH")
    private Long strength;

    @Column(name="SPEED")
    private Long speed;

    @Column(name="ARMOR")
    private Long armor;

    @Enumerated(EnumType.STRING)
    @Column(name="SLOT")
    private FighterSlot slot;

}
