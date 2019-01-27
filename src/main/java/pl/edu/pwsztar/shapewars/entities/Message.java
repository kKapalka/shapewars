package pl.edu.pwsztar.shapewars.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="MESSAGE")
public class Message {

    @Id
    @GeneratedValue
    @Column(name="MESSAGE_ID")
    private Long ID;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable
            (
                    name="MESSAGE_PLAYERS",
                    joinColumns={ @JoinColumn(name="MESSAGE_ID", referencedColumnName="MESSAGE_ID") },
                    inverseJoinColumns={ @JoinColumn(name="USER_ID", referencedColumnName="USER_ID") }
            )
    private List<User> messagePlayers;

    @Column(name="MESSAGE_TIME")
    private LocalDateTime messageTime;

    @Column(name="MESSAGE")
    private String message;

    @Column(name="SENDER_NAME")
    private String senderName;
}
