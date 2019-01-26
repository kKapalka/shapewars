package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.SkillEffect;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;
import pl.edu.pwsztar.shapewars.entities.dto.SkillEffectBundleDto;
import pl.edu.pwsztar.shapewars.entities.dto.SkillEffectDto;
import pl.edu.pwsztar.shapewars.entities.enums.SkillStatusEffect;
import pl.edu.pwsztar.shapewars.entities.enums.TargetType;
import pl.edu.pwsztar.shapewars.entities.enums.ValueModifierType;
import pl.edu.pwsztar.shapewars.repositories.SkillEffectRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillEffectService {

    @Autowired
    private SkillEffectRepository skillEffectRepository;

    public List<SkillEffect> createSkillEffects(SkillEffectBundle bundle,SkillEffectBundleDto dto){
        return dto.getSkillEffectDtos().stream().map(this::updateSkillEffect)
                .peek(skillEffect -> skillEffect.setSkillEffectBundle(bundle)).collect(Collectors.toList());
    }
    public SkillEffect updateSkillEffect(SkillEffectDto dto){
        SkillEffect skillEffect = new SkillEffect();
        if(dto.getId()!=null){
            skillEffect=skillEffectRepository.getOne(dto.getId());
        }
        skillEffect.setMinValue(dto.getMinValue());
        skillEffect.setMaxValue(dto.getMaxValue());
        skillEffect.setTargetType(TargetType.valueOf(dto.getTargetType()));
        skillEffect.setSkillStatusEffect(SkillStatusEffect.valueOf(dto.getSkillStatusEffect()));
        skillEffect.setValueModifierType(ValueModifierType.valueOf(dto.getValueModifierType()));
        return skillEffect;
    }
}
