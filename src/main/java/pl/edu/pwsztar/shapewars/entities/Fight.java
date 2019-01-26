package pl.edu.pwsztar.shapewars.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pwsztar.shapewars.entities.enums.FightStatus;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="FIGHT")
public class Fight {

    @Id
    @GeneratedValue
    @Column(name="FIGHT_ID")
    private Long ID;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable
    (
    name="FIGHT_PLAYERS",
    joinColumns={ @JoinColumn(name="FIGHT_ID", referencedColumnName="FIGHT_ID") },
    inverseJoinColumns={ @JoinColumn(name="USER_ID", referencedColumnName="USER_ID") }
    )
    private List<User> fightingPlayers;

    @Enumerated(EnumType.STRING)
    @Column(name="FIGHT_STATUS")
    private FightStatus fightStatus;

    @Column(name="WINNER_NAME")
    private String winnerName;
}
