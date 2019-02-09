package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.Skill;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {

    @Query("select s from Skill s where s.active=true")
    List<Skill> findAll();
}
