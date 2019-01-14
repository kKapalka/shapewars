package pl.edu.pwsztar.shapewars.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.FightAction;
import pl.edu.pwsztar.shapewars.entities.dto.ActionDto;
import pl.edu.pwsztar.shapewars.repositories.FightActionRepository;
import pl.edu.pwsztar.shapewars.utilities.SkillEvaluator;

import javax.persistence.EntityNotFoundException;

@Service
public class ActionService {

    @Autowired
    private FightActionRepository fightActionRepository;

    @Autowired
    private FighterService fighterService;

    @Autowired
    private FightService fightService;

    @Autowired
    private SkillService skillService;

    public ActionDto save(ActionDto dto){
        FightAction fightAction = updateAction(dto);
        FightAction newFightAction = fightActionRepository.save(fightAction);
        return ActionDto.fromEntity(newFightAction);
    }
    public List<FightAction> getActionsForFight(Long id){
        //żeby było po kolei
        List<FightAction> fightActions = fightActionRepository.findAllByFight(fightService.findFightById(id));
        fightActions.sort((a,b)->(int)(a.getId()-b.getId()));
        return fightActions;
    }
    private FightAction updateAction(ActionDto dto){
        FightAction fightAction = new FightAction();
        if(dto.getId()!=null){
            fightAction = fightActionRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        }
        fightAction.setFight(fightService.findFightById(dto.getFightId()));
        if(dto.getSkillId()!=0){
            //skillId=0 -> postać nic nie robi/jest ogłuszona
            fightAction.setSkill(skillService.getSkillsByIdIn(Arrays.asList(dto.getSkillId())).get(0));
        }
        fightAction.setActiveFighter(fighterService.getFighterById(dto.getActiveFighterId()));
        if(dto.getSelectedTargetId()!=0) {
            fightAction.setSelectedTarget(fighterService.getFighterById(dto.getSelectedTargetId()));
        }
        if(dto.getNextActiveFighterId()!=0){
            //nie ma następnego wojownika -> trzeba utworzyć nową kolejkę
            fightAction.setNextActiveFighter(fighterService.getFighterById(dto.getNextActiveFighterId()));
        }
        fightAction.setActionTime(LocalDateTime.now());
        if(dto.getSkillId()!=0){
            //jak ogłuszony to nic nie robi
            fightAction.setResultSet(SkillEvaluator.perform(fightAction));
        }
        return fightAction;
    }

}
