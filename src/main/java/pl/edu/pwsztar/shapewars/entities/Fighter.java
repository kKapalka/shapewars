package pl.edu.pwsztar.shapewars.entities;

import lombok.*;
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
    @JoinColumn(name="FIGHTER_MODEL_REFERRENCE_ID")
    private FighterModelReference fighterModelReferrence;

    @ManyToOne
    @JoinColumn(name="OWNER_ID")
    private User owner;

    @Column(name="LEVEL")
    private Long level;

    @Column(name="XP_POINTS")
    private Long experiencePoints;

    @Column(name="HITPOINTS_MOD")
    private Long hitPointsModifier;

    @Column(name="STRENGTH_MOD")
    private Long strengthModifier;

    @Column(name="ARMOR_MOD")
    private Long armorModifier;

    @Enumerated(EnumType.STRING)
    @Column(name="SLOT")
    private FighterSlot slot;
}
