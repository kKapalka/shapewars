package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.ColorMap;
import pl.edu.pwsztar.shapewars.entities.FighterModelReference;
import pl.edu.pwsztar.shapewars.entities.Shape;

import java.util.List;
import java.util.Optional;

@Repository
public interface FighterModelReferenceRepository extends JpaRepository<FighterModelReference,Long> {

    Optional<FighterModelReference> findByShapeAndColor(Shape shape, ColorMap color);

    List<FighterModelReference> findAllByShape(Shape shape);
    List<FighterModelReference> findAllByColor(ColorMap color);
}
