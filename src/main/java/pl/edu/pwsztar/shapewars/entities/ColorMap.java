package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="COLOR")
public class ColorMap {
    @Id
    @GeneratedValue
    @Column(name="COLOR_ID")
    private Long ID;

    @Column(name="COLOR_NAME")
    private String colorName;

    @Column(name="COLOR_MAP")
    private byte[] colorMap;

    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name="COLOR_ID", referencedColumnName = "COLOR_ID", nullable = false)
    private List<ColorDamage> colorDamageList;

}
