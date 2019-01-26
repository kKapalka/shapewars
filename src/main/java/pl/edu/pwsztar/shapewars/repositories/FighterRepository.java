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

    @Query("select f from Fighter f where f.owner.login=?1")
    List<Fighter> findAllByOwnerName(String ownerName);

    @Transactional
    @Query("delete from Fighter f where f.owner.login=?1")
    List<Fighter> deleteAllByOwnerName(String ownerName);

    @Query("select f from Fighter f where f.slot<>'INVENTORY' and f.owner.login=?1")
    List<Fighter> findCombatantsForUserName(String ownerName);
}
