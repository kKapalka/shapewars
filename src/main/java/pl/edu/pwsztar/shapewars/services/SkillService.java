package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.Changelog;
import pl.edu.pwsztar.shapewars.entities.Skill;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;
import pl.edu.pwsztar.shapewars.entities.dto.FighterDto;
import pl.edu.pwsztar.shapewars.entities.dto.SkillDto;
import pl.edu.pwsztar.shapewars.repositories.ChangelogRepository;
import pl.edu.pwsztar.shapewars.repositories.SkillRepository;
import pl.edu.pwsztar.shapewars.utilities.ChangelogUtility;
import pl.edu.pwsztar.shapewars.utilities.TooltipCreator;

import javax.persistence.EntityNotFoundException;
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

    @Autowired
    private ChangelogRepository changelogRepository;

    public SkillDto save(SkillDto skillDto){
        Changelog changelog =
              ChangelogUtility.compute(skillDto.getId()==null?new Skill():
                                       skillRepository.findById(skillDto.getId()).orElse(new Skill()),skillDto);
        Skill skill = updateSkill(skillDto);
        SkillDto newDto = SkillDto.fromEntity(skillRepository.save(skill));
        if(!changelog.getChange().equals("")) {
            changelogRepository.save(changelog);
        }
        return newDto;
    }

    public Skill updateSkill(SkillDto dto){
        Skill skill = new Skill();
        if(dto.getId()!=null){
            skill=skillRepository.getOne(dto.getId());
        }
        skill.setName(dto.getName());
        skill.setCost(dto.getCost());
        skill.setIcon(dto.getIcon().getBytes());
        skill.setSkillEffectBundles(skillEffectBundleService.createSkillEffectBundles(skill,dto));
        skill.setTooltip(TooltipCreator.createTooltip(skill));
        return skill;
    }

    public List<SkillDto> getFighterSkills(FighterDto fighterDto){
        return skillRepository.findAllById(fighterDto.getFighterModelReferrenceDto().getSkillSet()).stream().map(SkillDto::fromEntity
        ).collect(Collectors.toList());
    }

    public List<Skill> getSkillsByIdIn(List<Long> ids){
        return skillRepository.findAll().stream()
                .filter(skill->ids.contains(skill.getID())).collect(Collectors.toList());
    }

    public List<Skill> getAll(){
        return skillRepository.findAll();
    }

    public SkillDto getSkillById(Long id){
        return SkillDto.fromEntity(skillRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }
    public void deleteSkillById(Long id){
        Skill skill = skillRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        skill.setActive(false);
        skillRepository.save(skill);
    }
}
