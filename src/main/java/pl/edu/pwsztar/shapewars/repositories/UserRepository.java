package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    Optional<User> findByLogin(String login);
}
