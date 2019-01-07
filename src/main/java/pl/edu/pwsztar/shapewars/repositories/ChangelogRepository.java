package pl.edu.pwsztar.shapewars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwsztar.shapewars.entities.Changelog;

@Repository
public interface ChangelogRepository extends JpaRepository<Changelog,Long> {

}
