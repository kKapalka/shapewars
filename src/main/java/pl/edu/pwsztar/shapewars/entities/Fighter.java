package pl.edu.pwsztar.shapewars.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import pl.edu.pwsztar.shapewars.entities.enums.ColorType;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name="FIGHTER")
public class Fighter {

    @Id
    @Generated
    @Column(name="FIGHTER_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="SHAPE_ID")
    private Shape shape;

    @Column(name="LEVEL")
    private Long level;

    @Column(name="XP_POINTS")
    private Long experiencePoints;

    @Column(name="COLOR")
    @Enumerated(EnumType.STRING)
    private ColorType color;

    @Column(name="HITPOINTS")
    private Long hitPoints;

    @Column(name="DAMAGE")
    private Long damage;

}
