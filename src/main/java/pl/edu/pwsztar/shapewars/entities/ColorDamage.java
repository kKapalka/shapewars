package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="COLOR_DAMAGE")
public class ColorDamage {
    @Id
    @GeneratedValue
    @Column(name="COLOR_DAMAGE_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="COLOR_ID")
    private ColorMap color;

    @ManyToOne
    @JoinColumn(name="ENEMY_COLOR_ID")
    private ColorMap enemyColor;

    @Column(name="DAMAGE_PERCENTAGE")
    private Long damagePercentage = 100L;
}
