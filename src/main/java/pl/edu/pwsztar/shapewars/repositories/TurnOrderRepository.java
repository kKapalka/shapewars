package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.TurnOrder;

import java.util.List;

public interface TurnOrderRepository extends JpaRepository<TurnOrder,Long> {

    @Query("select t from TurnOrder t where t.fight=?1 and t.turn=?2")
    List<TurnOrder> getTurnOrderForFightByTurnNumber(Fight fight, Long turn);
}
