package pl.edu.pwsztar.shapewars.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.Action;
import pl.edu.pwsztar.shapewars.entities.dto.ActionDto;
import pl.edu.pwsztar.shapewars.entities.dto.FightDto;
import pl.edu.pwsztar.shapewars.repositories.ActionRepository;
import pl.edu.pwsztar.shapewars.utilities.SkillEvaluator;

@Service
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private FighterService fighterService;

    @Autowired
    private FightService fightService;

    @Autowired
    private SkillService skillService;

    public ActionDto save(ActionDto dto){
        Action action = updateAction(dto);
        return ActionDto.fromEntity(actionRepository.save(action));
    }
    public List<Action> getActionsForFight(Long id){
        return actionRepository.findAllByFight(fightService.findFightById(id));
    }
    private Action updateAction(ActionDto dto){
        Action action = new Action();
        if(dto.getId()!=null){
            action=actionRepository.getOne(dto.getId());
        }
        action.setID(dto.getId());
        action.setSkill(skillService.getSkillsByIdIn(Arrays.asList(dto.getSkillId())).get(0));
        action.setActiveFighter(fighterService.getFighterById(dto.getActiveFighterId()));
        action.setSelectedTarget(fighterService.getFighterById(dto.getSelectedTargetId()));
        action.setNextActiveFighter(fighterService.getFighterById(dto.getNextActiveFighterId()));
        action.setActionTime(LocalDateTime.now());
        action.setFight(fightService.findFightById(dto.getFightId()));
        action.setTargetStatuses(SkillEvaluator.perform(action));
        return action;
    }

}
