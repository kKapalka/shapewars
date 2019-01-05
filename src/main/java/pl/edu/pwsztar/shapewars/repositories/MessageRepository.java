package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.Message;
import pl.edu.pwsztar.shapewars.entities.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("Select m from Message m where m.receiver=?1 or m.sender=?1")
    List<Message> getAllBySenderOrReceiver(User user);

    @Query("Select m from Message m where (m.receiver=?1 and m.sender=?2) or (m.receiver=?2 and m.sender=?1)")
    List<Message> getAllByCallers(User caller1, User caller2);

}
