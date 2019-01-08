package pl.edu.pwsztar.shapewars.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.dto.ChangelogDto;
import pl.edu.pwsztar.shapewars.repositories.ChangelogRepository;

@Service
public class ChangelogService {

    @Autowired
    private ChangelogRepository changelogRepository;

    public List<ChangelogDto> getAll(){
        return changelogRepository.findAll().stream().map(ChangelogDto::fromEntity).collect(Collectors.toList());
    }

}
