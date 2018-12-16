package pl.edu.pwsztar.shapewars.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pwsztar.shapewars.entities.enums.Colors;
import pl.edu.pwsztar.shapewars.entities.enums.MessageType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="MAINTENANCE_LOG")
@NoArgsConstructor
public class MaintenanceLog {

    @Id
    @GeneratedValue
    @Column(name="MAINTENANCE_LOG_TIME")
    private Long ID;

    @Column(name="MESSAGE_TIME")
    private LocalDateTime messageTime;

    @ManyToOne
    @JoinColumn(name="INFORMER_ID")
    private User informer;

    @Column(name="MESSAGE")
    private String message;

    @Column(name="MESSAGE_TYPE")
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

}
