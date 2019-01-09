package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.ColorMap;

import java.util.Optional;

@Repository
public interface ColorMapRepository extends JpaRepository<ColorMap,Long> {

    Optional<ColorMap> findByColorName(String name);
}
