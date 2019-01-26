package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.TurnOrder;

import java.util.List;

@Repository
public interface TurnOrderRepository extends JpaRepository<TurnOrder,Long> {

    @Query("select t from TurnOrder t where t.fight.id=?1 and t.turn=?2")
    List<TurnOrder> getTurnOrderForFightByFightIdAndTurnNumber(Long fightId, Long turn);
}
