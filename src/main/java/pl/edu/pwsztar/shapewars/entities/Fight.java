package pl.edu.pwsztar.shapewars.entities;


import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import pl.edu.pwsztar.shapewars.entities.enums.FightStatus;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="FIGHT")
public class Fight {

    @Id
    @GeneratedValue
    @Column(name="FIGHT_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="PLAYER_ONE_ID")
    private User playerOne;

    @ManyToOne
    @JoinColumn(name="PLAYER_TWO_ID")
    private User playerTwo;

    @Enumerated(EnumType.STRING)
    @Column(name="FIGHT_STATUS")
    private FightStatus fightStatus;

    @Override
    public String toString() {
        return "Fight{" +
                "ID=" + ID +
                ", playerOne=" + playerOne.getLogin() +
                ", playerTwo=" + (playerTwo!=null?playerTwo.getLogin():null) +
                ", fightStatus=" + fightStatus +
                '}';
    }
}
