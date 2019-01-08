package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
