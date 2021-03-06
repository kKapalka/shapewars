package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Skill;
import pl.edu.pwsztar.shapewars.entities.SkillEffect;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;
import pl.edu.pwsztar.shapewars.entities.dto.SkillDto;
import pl.edu.pwsztar.shapewars.entities.dto.SkillEffectBundleDto;
import pl.edu.pwsztar.shapewars.repositories.SkillEffectBundleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillEffectBundleService {

    @Autowired
    private SkillEffectBundleRepository skillEffectBundleRepository;

    @Autowired
    private SkillEffectService skillEffectService;

    public List<SkillEffectBundle> createSkillEffectBundles(Skill skill, SkillDto skillDto){
        return skillDto.getSkillEffectBundles().stream().map(this::updateSkillEffectBundle)
                .peek(bundle->bundle.setSkill(skill)).collect(Collectors.toList());
    }
    private SkillEffectBundle updateSkillEffectBundle(SkillEffectBundleDto dto){
        SkillEffectBundle skillEffectBundle = new SkillEffectBundle();
        if(dto.getId()!=null){
            skillEffectBundle=skillEffectBundleRepository.getOne(dto.getId());
        }
        skillEffectBundle.setAccuracy(dto.getAccuracy());
        skillEffectBundle.setSkillEffects(skillEffectService.createSkillEffects(skillEffectBundle,dto));
        return skillEffectBundle;
    }
}
