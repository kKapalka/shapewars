package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;
import pl.edu.pwsztar.shapewars.entities.dto.SkillDto;
import pl.edu.pwsztar.shapewars.services.SkillEffectBundleService;
import pl.edu.pwsztar.shapewars.services.SkillEffectService;
import pl.edu.pwsztar.shapewars.services.SkillService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("skill")
@CrossOrigin("*")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillEffectBundleService skillEffectBundleService;

    @Autowired
    private SkillEffectService skillEffectService;


    @PostMapping("save")
    public SkillDto save(@RequestBody SkillDto skillDto){
        return skillService.save(skillDto);
    }

    @GetMapping("all")
    public List<SkillDto> getAll(){
        return skillService.getAll().stream().map(SkillDto::fromEntity).collect(Collectors.toList());
    }

}
