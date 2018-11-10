package pl.edu.pwsztar.shapewars.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USERS")
public class User {

    @Id
    @Generated
    @Column(name="USER_ID")
    private Long ID;

    @Column(name="LOGIN")
    private String login;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="EMAIL")
    private String email;

    @Column(name="VERIFIED")
    private boolean verified;

    @Column(name="LEVEL")
    private Long level;

    @Column(name="XP_POINTS")
    private Long experiencePoints;

    @ManyToMany
    @JoinTable(name = "USER_FIGHTER",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "FIGHTER_ID")
    )
    private List<Fighter> fighterList;

    @ManyToMany
    @JoinTable(name = "USER_FIGHTER_CURRENT",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "FIGHTER_ID")
    )
    private List<Fighter> currentFighters;



}
