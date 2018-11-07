package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.shapewars.entities.Skill;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.dto.SkillDto;
import pl.edu.pwsztar.shapewars.repositories.SkillRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public void save(SkillDto skillDto){
        Skill skill= Skill.builder().name(skillDto.getName())
                .tooltip(skillDto.getTooltip()).build();
        if(skillDto.getId() != null){
            skill.setID(skillDto.getId());
        }
        skillRepository.save(skill);
    }
    public List<SkillDto> getFighterSkills(FighterDto fighterDto){
        return skillRepository.findAllById(fighterDto.getShapeSkillsetIDs()).stream().map(SkillDto::fromEntity
        ).collect(Collectors.toList());
    }
    public SkillDto getFighterSpecialSkill(FighterDto fighterDto){
        return SkillDto.fromEntity(skillRepository.findById(fighterDto.getShapeSpecialSkillID()).orElseThrow(EntityNotFoundException::new));
    }

}
