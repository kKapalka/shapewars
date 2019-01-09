package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.ColorDamage;

@Repository
public interface ColorDamageRepository extends JpaRepository<ColorDamage,Long> {
}
