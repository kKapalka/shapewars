package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.active=true")
    List<User> findAll();

    @Query("select u from User u where u.email=?1 and u.active=true")
    User findByEmail(String email);

    @Query("select u from User u where u.login=?1 and u.active=true")
    Optional<User> findByLoginEquals(String login);

    @Query("select u from User u where u.login in ?1 and u.active=true")
    List<User> findAllByLoginIn(List<String> login);

    @Query("select u from User u where u.email=?1 and u.active=true")
    List<User> findAllByEmail(String email);
}
