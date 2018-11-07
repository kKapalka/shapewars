package pl.edu.pwsztar.shapewars.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name="PLAYER")
public class Player {

    @Id
    @Generated
    @Column(name="PLAYER_ID")
    private Long ID;

    @OneToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Column(name="LEVEL")
    private Long level;

    @Column(name="XP_POINTS")
    private Long experiencePoints;

    @ManyToMany
    @JoinTable(name = "PLAYER_FIGHTER",
            joinColumns = @JoinColumn(name = "PLAYER_ID"),
            inverseJoinColumns = @JoinColumn(name = "FIGHTER_ID")
    )
    private List<Fighter> fighterList;

    @ManyToMany
    @JoinTable(name = "PLAYER_FIGHTER_CURRENT",
            joinColumns = @JoinColumn(name = "PLAYER_ID"),
            inverseJoinColumns = @JoinColumn(name = "FIGHTER_ID")
    )
    private List<Fighter> currentFighters;

}
