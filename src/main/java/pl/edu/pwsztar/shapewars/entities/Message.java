package pl.edu.pwsztar.shapewars.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="MESSAGE")
public class Message {

    @Id
    @GeneratedValue
    @Column(name="MESSAGE_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User sender;

    @ManyToOne
    @JoinColumn(name="RECEIVER_ID")
    private User receiver;

    @Column(name="MESSAGE_TIME")
    private LocalDateTime messageTime;

    @Column(name="MESSAGE")
    private String message;


}
