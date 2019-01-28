package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.Agent;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent,Long> {

    @Query("select a from Agent a where a.dedicatedPlayer.login=?1")
    Optional<Agent> findByUsername(String username);
}
