package pl.edu.pwsztar.shapewars.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
