package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.Shape;
import pl.edu.pwsztar.shapewars.entities.ColorMap;

import java.util.List;
import javax.transaction.Transactional;

@Repository
public interface FighterRepository extends JpaRepository<Fighter,Long> {

    List<Fighter> findAllByOwner(User owner);

    @Transactional
    List<Fighter> deleteAllByOwner(User owner);

    @Query("select f from Fighter f where f.slot<>'INVENTORY' and f.owner=?1")
    List<Fighter> findCombatantsForUser(User user);
}
