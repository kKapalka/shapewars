package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.edu.pwsztar.shapewars.entities.FightAction;
import pl.edu.pwsztar.shapewars.entities.Fight;

import java.util.List;

@Repository
public interface FightActionRepository extends JpaRepository<FightAction,Long> {

    @Query("select f from FightAction f where f.fight.id=?1")
    List<FightAction> findAllByFight(Long fightId);
}
