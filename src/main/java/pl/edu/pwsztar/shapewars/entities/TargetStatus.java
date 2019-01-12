package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="TARGET_STATUS")
public class TargetStatus {

    @Id
    @GeneratedValue
    @Column(name="TARGET_STATUS_ID")
    private Long ID;

    @ManyToOne
    @JoinColumn(name="TARGET_ID")
    private Fighter target;

    @OneToMany (cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="TARGET_STATUS_ID", referencedColumnName = "TARGET_STATUS_ID", nullable = false)
    private List<SkillEffectResult> skillEffectResultList;
}
