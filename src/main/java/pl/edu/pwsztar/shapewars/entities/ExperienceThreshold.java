package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="EXP_THRESHOLD")
public class ExperienceThreshold {

    @Id
    @GeneratedValue
    @Column(name="EXP_THRESHOLD_ID")
    private Long ID;

    @Column(name="LEVEL")
    private Long level;

    @Column(name="THRESHOLD")
    private Long threshold;

}
