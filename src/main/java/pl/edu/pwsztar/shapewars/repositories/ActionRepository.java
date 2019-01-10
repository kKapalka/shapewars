package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.edu.pwsztar.shapewars.entities.Action;
import pl.edu.pwsztar.shapewars.entities.Fight;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action,Long> {
    List<Action> findAllByFight(Fight fight);
}
