package pl.edu.pwsztar.shapewars.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.edu.pwsztar.shapewars.entities.Fight;
import pl.edu.pwsztar.shapewars.entities.User;

import javax.transaction.Transactional;

@Repository
public interface FightRepository extends JpaRepository<Fight,Long> {

    @Query("select f from Fight f where (f.playerOne=?1 or f.playerTwo=?1)")
    List<Fight> findByUser(User user);

    @Query("select f from Fight f where (f.playerTwo=?1 and f.fightStatus='INVITE_PENDING')")
    List<Fight> findChallengesForUser(User user);

    @Query("select f from Fight f where (f.playerOne=?1 and f.fightStatus='INVITE_PENDING')")
    List<Fight> findByChallenger(User user);

    @Query("select f from Fight f where (f.playerOne=?1 and f.playerTwo=?2 and f.fightStatus='INVITE_PENDING')")
    List<Fight> findChallengeByFightingSides(User playerOne, User playerTwo);

    @Query("select f from Fight f where (f.playerOne in ?1 or f.playerTwo in ?1 and f.fightStatus='INVITE_PENDING')")
    List<Fight> findAllPendingInvitesForPlayers(List<User> users);

    @Modifying
    @Transactional
    @Query("update Fight set fightStatus='INVITE_REJECTED' where ID in ?1")
    void updateFightsSetAsAbandoned(List<Long> fights);
}
