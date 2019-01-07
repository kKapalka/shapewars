package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.Shape;
import pl.edu.pwsztar.shapewars.entities.ColorMap;

import java.util.List;

@Repository
public interface FighterRepository extends JpaRepository<Fighter,Long> {

    List<Fighter> findAllByOwner(User owner);

    List<Fighter> findAllByShape(Shape shape);

    List<Fighter> findAllByColor(ColorMap color);

}
