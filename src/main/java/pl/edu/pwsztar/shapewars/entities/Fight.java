package pl.edu.pwsztar.shapewars.entities;


import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import pl.edu.pwsztar.shapewars.entities.enums.FightStatus;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name="FIGHT")
public class Fight {

    @Id
    @Generated
    @Column(name="FIGHT_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="PLAYER_ONE_ID")
    private Player playerOne;

    @ManyToOne
    @JoinColumn(name="PLAYER_TWO_ID")
    private Player playerTwo;

    @Enumerated(EnumType.STRING)
    @Column(name="FIGHT_STATUS")
    private FightStatus fightStatus;

}
