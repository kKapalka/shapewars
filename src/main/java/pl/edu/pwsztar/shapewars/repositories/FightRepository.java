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

    @Query("select f from Fight f where f.playerOne.login=?1 or f.playerTwo.login=?1")
    List<Fight> findByUser(String userName);

    @Query("select f from Fight f where f.playerOne.login=?1")
    List<Fight> findBotFightsByUser(String userName);

    @Query("select f from Fight f where (f.playerTwo.login=?1 and f.fightStatus='INVITE_PENDING')")
    List<Fight> findChallengesForUser(String userName);

    @Query("select f from Fight f where (f.playerOne.login=?1 and f.fightStatus='INVITE_PENDING')")
    List<Fight> findByChallenger(String userName);

    @Query("select f from Fight f where (f.playerOne.login=?1 and f.playerTwo.login=?2 and f.fightStatus='INVITE_PENDING')")
    List<Fight> findChallengeByFightingSides(String playerOneName, String playerTwoName);

    @Query("select f from Fight f where (f.playerOne.login in ?1 or f.playerTwo.login in ?1 and f.fightStatus='INVITE_PENDING')")
    List<Fight> findAllPendingInvitesForPlayers(List<String> userNames);

    @Query("select f from Fight f where (f.playerOne.login=?1 or f.playerTwo.login=?1) and f.fightStatus='IN_PROGRESS'")
    List<Fight> findFightInProgressForUser(String userName, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Fight set fightStatus='INVITE_REJECTED' where ID in ?1")
    void updateFightsSetAsAbandoned(List<Long> fights);
}
