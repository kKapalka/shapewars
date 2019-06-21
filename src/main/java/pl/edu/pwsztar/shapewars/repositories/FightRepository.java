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

    @Query("select f from Fight f where (select u from User u where u.login = ?1) member of f.fightingPlayers")
    List<Fight> findByUser(String userName);

    @Query("select f from Fight f where (select u from User u where u.login = ?1) member of f.fightingPlayers and f.fightStatus='INVITE_PENDING'")
    List<Fight> findChallengesForUser(String userName);

    @Query("select f from Fight f where (select u from User u where u.login = ?1) member of f.fightingPlayers or (select u from User u where u.login = ?2) member of f.fightingPlayers and f.fightStatus='INVITE_PENDING'")
    List<Fight> findChallengeByFightingSides(String playerName1, String playerName2);

    @Query("select f from Fight f where (select u from User u where u.login = ?1) member of f.fightingPlayers and f.fightStatus='IN_PROGRESS'")
    List<Fight> findFightInProgressForUser(String userName, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Fight set fightStatus='INVITE_REJECTED' where ID in ?1")
    void updateFightsSetAsAbandoned(List<Long> fights);
}
