package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.pwsztar.shapewars.utilities.FighterImageGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name="FIGHTER_MODEL_REFERRENCE")
@NoArgsConstructor
public class FighterModelReference {

    @Id
    @GeneratedValue
    @Column(name="FIGHTER_MODEL_REFERRENCE_ID")
    private Long ID;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="SHAPE_ID")
    private Shape shape;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="COLOR_ID")
    private ColorMap color;

    @Column(name="FIGHTER_IMAGE")
    private byte[] fighterImage;

    @PreUpdate
    void onUpdate(){
        this.fighterImage= FighterImageGenerator.generateImageFrom(shape,color);
    }
}
