package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.Message;
import pl.edu.pwsztar.shapewars.entities.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("Select m from Message m where (select u from User u where u.login = ?1) member of m.messagePlayers")
    List<Message> getAllBySenderOrReceiver(String login);

    @Query("Select m from Message m where (select u from User u where u.login = ?1) member of m.messagePlayers and (select u from User u where u.login = ?2) member of m.messagePlayers")
    List<Message> getAllByCallers(String caller1, String caller2);

}
