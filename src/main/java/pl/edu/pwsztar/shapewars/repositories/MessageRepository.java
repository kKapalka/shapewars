package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.Message;
import pl.edu.pwsztar.shapewars.entities.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("Select m from Message m where m.receiver.login=?1 or m.sender.login=?1")
    List<Message> getAllBySenderOrReceiver(String login);

    @Query("Select m from Message m where m.receiver.login in ?1 and m.sender.login in ?1")
    List<Message> getAllByCallers(List<String> callerNames);

}
