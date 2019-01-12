package pl.edu.pwsztar.shapewars.utilities;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import pl.edu.pwsztar.shapewars.entities.*;
import pl.edu.pwsztar.shapewars.entities.enums.FighterSlot;
import pl.edu.pwsztar.shapewars.entities.enums.TargetType;

public class SkillEvaluator {

    private static List<Fighter> allies, enemies;
    private static List<TargetStatus> statuses;
    private static Action currentAction;
    public static List<TargetStatus> perform(Action action){
        currentAction=action;
        statuses = new ArrayList<>();
        if(getFighterList(action.getFight().getPlayerOne()).contains(action.getActiveFighter())){
            allies=getFighterList(action.getFight().getPlayerOne());
            enemies=getFighterList(action.getFight().getPlayerTwo());
        } else{
            enemies=getFighterList(action.getFight().getPlayerOne());
            allies=getFighterList(action.getFight().getPlayerTwo());
        }
        action.getSkill().getSkillEffectBundles().stream().forEach(SkillEvaluator::processBundle);
        return sortStatuses();
    }

    private static List<Fighter> getFighterList(User user){
        return user.getFighterList().stream().filter(fighter->fighter.getSlot()!=FighterSlot.INVENTORY).collect(
              Collectors.toList());
    }

    private static void processBundle(SkillEffectBundle bundle){
        bundle.getSkillEffects().forEach(effect->{
            List<Fighter> eligibleTargets=new ArrayList<>();
            if(effect.getTargetType() == TargetType.ALL_UNITS){
                eligibleTargets.addAll(allies);
                eligibleTargets.addAll(enemies);
            } else if(effect.getTargetType()==TargetType.ALL_ALLIED_UNITS){
                eligibleTargets.addAll(allies);
            } else if(effect.getTargetType()==TargetType.ALL_ENEMY_UNITS){
                eligibleTargets.addAll(enemies);
            } else if(effect.getTargetType()==TargetType.RANDOM_ALLY){
                eligibleTargets.add(allies.get(new Random().nextInt(allies.size())));
            } else if(effect.getTargetType()==TargetType.RANDOM_ENEMY){
                eligibleTargets.add(enemies.get(new Random().nextInt(enemies.size())));
            } else {
                eligibleTargets.add(currentAction.getSelectedTarget());
            }
            eligibleTargets=eligibleTargets.stream().distinct().collect(Collectors.toList());
            eligibleTargets.forEach(target->{
                TargetStatus newTargetStatus=new TargetStatus();
                newTargetStatus.setTarget(target);
                SkillEffectResult result = new SkillEffectResult();
                result.setSkillEffect(effect);
                if(new Random().nextInt(100)>bundle.getAccuracy()){
                    result.setResult((double) 0);
                } else{
                    result.setResult((new Random().nextDouble()*(effect.getMaxValue()-effect.getMinValue()))+effect.getMinValue());
                }
                newTargetStatus.setSkillEffectResultList(Arrays.asList(result));
                statuses.add(newTargetStatus);
            });
        });
    }
    private static List<TargetStatus> sortStatuses(){
        //TODO to jest brzydki kod, ale działający przymajmniej
        List<TargetStatus> sortedTargetStatus = new ArrayList<>();
        statuses.stream().forEach(status->{
            if(sortedTargetStatus.stream().filter
                    (sortedStatus->sortedStatus.getTarget()!=status.getTarget()).collect(Collectors.toList()).isEmpty()){
                sortedTargetStatus.add(status);
            } else{
                TargetStatus targetStatus =  sortedTargetStatus.stream().filter
                        (sortedStatus->sortedStatus.getTarget()!=status.getTarget())
                        .findFirst()
                        .get();
                List<SkillEffectResult> results = new ArrayList<>();
                results.addAll(targetStatus.getSkillEffectResultList());
                results
                        .add(status
                                .getSkillEffectResultList()
                        .get(0));
                targetStatus.setSkillEffectResultList(results);
            }
        });
        return sortedTargetStatus;
    }
}
