package pl.edu.pwsztar.shapewars.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.User;

@Repository
public interface FightRepository extends JpaRepository<Fight,Long> {

    @Query("select f from Fight f where (f.playerOne=?1 and f.fightStatus like 'VICTORY_PLAYER_ONE')" +
           " or (f.playerTwo=?1 and f.fightStatus like 'VICTORY_PLAYER_TWO')")
    List<Fight> findAllWonByUser(User user);

    @Query("select f from Fight f where (f.playerTwo=?1 and f.fightStatus like 'VICTORY_PLAYER_ONE')" +
           " or (f.playerOne=?1 and f.fightStatus like 'VICTORY_PLAYER_TWO')")
    List<Fight> findAllLostByUser(User user);

    @Query("select f from Fight f where (f.playerOne=?1 or f.playerOne=?1) and f.fightStatus like 'ABANDONED'")
    List<Fight> findAllAbandonedByUser(User user);

    @Query("select f from Fight f where (f.playerOne=?1 or f.playerOne=?1)")
    List<Fight> findByUser(User user);
}
