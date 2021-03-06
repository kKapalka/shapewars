package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;
import pl.edu.pwsztar.shapewars.entities.dto.SkillDto;
import pl.edu.pwsztar.shapewars.entities.enums.SkillStatusEffect;
import pl.edu.pwsztar.shapewars.entities.enums.TargetType;
import pl.edu.pwsztar.shapewars.entities.enums.ValueModifierType;
import pl.edu.pwsztar.shapewars.services.SkillEffectBundleService;
import pl.edu.pwsztar.shapewars.services.SkillEffectService;
import pl.edu.pwsztar.shapewars.services.SkillService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("skill")
@CrossOrigin
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("save")
    public SkillDto save(@RequestBody SkillDto skillDto){
        return skillService.save(skillDto);
    }

    @GetMapping("all")
    public List<SkillDto> getAll(){
        return skillService.getAll().stream().map(SkillDto::fromEntity).collect(Collectors.toList());
    }
    @GetMapping("{id}")
    public SkillDto get(@PathVariable Long id){
        return skillService.getSkillById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        skillService.deleteSkillById(id);
    }

    @GetMapping("effects")
    public List<SkillStatusEffect> getAllSkillStatusEffects(){
        return Arrays.asList(SkillStatusEffect.values());
    }
    @GetMapping("targets")
    public List<TargetType> getAllTargetTypes(){
        return Arrays.asList(TargetType.values());
    }
    @GetMapping("modifiers")
    public List<ValueModifierType> getAllValueModifierTypes(){
        return Arrays.asList(ValueModifierType.values());
    }
}
