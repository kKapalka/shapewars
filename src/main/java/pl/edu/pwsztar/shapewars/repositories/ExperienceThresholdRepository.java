package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.ExperienceThreshold;

@Repository
public interface ExperienceThresholdRepository extends JpaRepository<ExperienceThreshold,Long> {

    ExperienceThreshold getByLevel(Long level);

}
