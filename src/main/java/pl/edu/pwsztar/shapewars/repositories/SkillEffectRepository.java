package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.SkillEffect;

@Repository
public interface SkillEffectRepository extends JpaRepository<SkillEffect,Long> {
}
