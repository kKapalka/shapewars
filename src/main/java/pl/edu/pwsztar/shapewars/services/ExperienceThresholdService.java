package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.ExperienceThreshold;
import pl.edu.pwsztar.shapewars.entities.dto.ExperienceThresholdDto;
import pl.edu.pwsztar.shapewars.repositories.ExperienceThresholdRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ExperienceThresholdService {

    @Autowired
    private ExperienceThresholdRepository repository;

    public ExperienceThresholdDto save(ExperienceThresholdDto dto){
        ExperienceThreshold threshold = updateThreshold(dto);
        return ExperienceThresholdDto.fromEntity(repository.save(threshold));
    }

    private ExperienceThreshold updateThreshold(ExperienceThresholdDto dto) {
        ExperienceThreshold threshold = new ExperienceThreshold();
        if(dto.getId()!=null){
            threshold = getThresholdById(dto.getId());
        }
        threshold.setLevel(dto.getLevel());
        threshold.setThreshold(dto.getThreshold());
        return threshold;
    }

    public ExperienceThreshold getThresholdById(Long id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ExperienceThreshold> getAll(){
        return repository.findAll();
    }
    public ExperienceThreshold getByLevel(Long level){
        return repository.getByLevel(level);
    }
}
