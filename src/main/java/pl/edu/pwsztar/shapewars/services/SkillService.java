package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Skill;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.dto.SkillDto;
import pl.edu.pwsztar.shapewars.repositories.SkillRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public SkillDto save(SkillDto skillDto){
        Skill skill = updateSkill(skillDto);
        return SkillDto.fromEntity(skillRepository.save(skill));
    }

    public Skill updateSkill(SkillDto dto){
        Skill skill = new Skill();
        if(dto.getId()!=null){
            skill=skillRepository.getOne(dto.getId());
        }
        skill.setName(dto.getName());
//        skill.setTooltip(TooltipCreator.createTooltip(dto));
//        skill.setCost(dto.getCost());
//        skill.setSkillStatusEffect(SkillStatusEffect.valueOf(dto.getSkillEffect()));
//        skill.setTargetType(TargetType.valueOf(dto.getTargetType()));
//        skill.setMinValue(dto.getMinValue());
//        skill.setMaxValue(dto.getMaxValue());
//        skill.setAccuracy(dto.getAccuracy());
//        skill.setValueModifierType(ValueModifierType.valueOf(dto.getValueModifierType()));
        return skill;
    }

    public List<SkillDto> getFighterSkills(FighterDto fighterDto){
        return skillRepository.findAllById(fighterDto.getShapeSkillIDSet()).stream().map(SkillDto::fromEntity
        ).collect(Collectors.toList());
    }

}
