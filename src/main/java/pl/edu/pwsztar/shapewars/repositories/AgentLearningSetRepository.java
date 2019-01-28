package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.Agent;
import pl.edu.pwsztar.shapewars.entities.AgentLearningSet;

import java.util.Optional;

@Repository
public interface AgentLearningSetRepository extends JpaRepository<AgentLearningSet,Long> {

    @Query("select als from AgentLearningSet als where als.fight.id=?1")
    Optional<AgentLearningSet> findByFightId(Long id);
}
