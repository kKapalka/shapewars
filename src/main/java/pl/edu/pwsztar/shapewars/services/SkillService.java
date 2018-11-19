package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Skill;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.dto.SkillDto;
import pl.edu.pwsztar.shapewars.repositories.SkillRepository;
import pl.edu.pwsztar.shapewars.utilities.TooltipCreator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillEffectBundleService skillEffectBundleService;

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
        skill.setCost(dto.getCost());
        skill.setSkillEffectBundles(skillEffectBundleService.createSkillEffectBundles(skill,dto));
        skill.setTooltip(TooltipCreator.createTooltip(skill));
        System.out.println(skill);
        return skill;
    }

    public List<SkillDto> getFighterSkills(FighterDto fighterDto){
        return skillRepository.findAllById(fighterDto.getShapeSkillIDSet()).stream().map(SkillDto::fromEntity
        ).collect(Collectors.toList());
    }

    public List<Skill> getSkillsByIdIn(List<Long> ids){
        return skillRepository.findAll().stream()
                .filter(skill->ids.contains(skill.getID())).collect(Collectors.toList());
    }

    public List<Skill> getAll(){
        return skillRepository.findAll();
    }

}
