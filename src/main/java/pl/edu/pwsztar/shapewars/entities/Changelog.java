package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="CHANGELOG")
public class ColorMap {
    @Id
    @GeneratedValue
    @Column(name="CHANGELOG_ID")
    private Long ID;

    @Column(name="CHANGE_TIME")
    private LocalDateTime changeTime;

    @Column(name="CHANGE")
    private String change;

}
