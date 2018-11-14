package pl.edu.pwsztar.shapewars.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;
import pl.edu.pwsztar.shapewars.entities.dto.SkillDto;
import pl.edu.pwsztar.shapewars.services.SkillEffectBundleService;
import pl.edu.pwsztar.shapewars.services.SkillEffectService;
import pl.edu.pwsztar.shapewars.services.SkillService;

@RestController
@RequestMapping("skill")
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
}
