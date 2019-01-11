package pl.edu.pwsztar.shapewars.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TURN_ORDER")
public class TurnOrder {

    @Id
    @GeneratedValue
    @Column(name="TURN_ORDER_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="FIGHT_ID")
    private Fight fight;

    @ManyToOne
    @JoinColumn(name="FIGHTER_ID")
    private Fighter fighter;

    @Column(name="TURN")
    private Long turn;

    @Column(name="ORDER")
    private Long order;
}
