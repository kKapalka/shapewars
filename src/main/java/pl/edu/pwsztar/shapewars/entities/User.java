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
    @GeneratedValue
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

    @OneToMany (mappedBy = "owner",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fighter> fighterList;

    @Column(name="ADMIN")
    private boolean admin;

    @OneToMany (mappedBy = "informer",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<MaintenanceLog> maintenanceLogList;

    @OneToMany (mappedBy = "sender",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Message> messagesSent;

    @OneToMany (mappedBy = "receiver",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Message> messagesReceived;
}
