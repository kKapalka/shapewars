package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.dto.ExperienceThresholdDto;
import pl.edu.pwsztar.shapewars.services.ExperienceThresholdService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("thresholds")
@CrossOrigin
public class ThresholdController {

    @Autowired
    private ExperienceThresholdService experienceThresholdService;

    @PostMapping("save")
    public ExperienceThresholdDto save(@RequestBody ExperienceThresholdDto dto){
        return experienceThresholdService.save(dto);
    }

    @GetMapping("all")
    public List<ExperienceThresholdDto> getAll(){
        return experienceThresholdService.getAll().stream().map(ExperienceThresholdDto::fromEntity).collect(Collectors.toList());
    }

}
