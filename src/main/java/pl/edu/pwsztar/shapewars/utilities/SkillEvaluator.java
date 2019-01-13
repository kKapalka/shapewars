package pl.edu.pwsztar.shapewars.utilities;

import java.util.*;
import java.util.stream.Collectors;

import pl.edu.pwsztar.shapewars.entities.*;
import pl.edu.pwsztar.shapewars.entities.enums.FighterSlot;
import pl.edu.pwsztar.shapewars.entities.enums.TargetType;

public class SkillEvaluator {

    private static List<Fighter> allies, enemies;
    private static List<SkillEffectResult> resultSet;
    private static FightAction currentAction;
    public static List<SkillEffectResult> perform(FightAction fightAction){
        currentAction= fightAction;
        resultSet = new ArrayList<>();
        if(getFighterList(fightAction.getFight().getPlayerOne()).contains(fightAction.getActiveFighter())){
            allies=getFighterList(fightAction.getFight().getPlayerOne());
            enemies=getFighterList(fightAction.getFight().getPlayerTwo());
        } else{
            enemies=getFighterList(fightAction.getFight().getPlayerOne());
            allies=getFighterList(fightAction.getFight().getPlayerTwo());
        }
        fightAction.getSkill().getSkillEffectBundles().stream().forEach(SkillEvaluator::processBundle);
        return resultSet;
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
            } else if(effect.getTargetType()==TargetType.THIS_UNIT){
                eligibleTargets.add(currentAction.getActiveFighter());
            } else{
                eligibleTargets.add(currentAction.getSelectedTarget());
            }
            eligibleTargets=eligibleTargets.stream().distinct().collect(Collectors.toList());
            eligibleTargets.forEach(target->{
                SkillEffectResult result = new SkillEffectResult();
                result.setTarget(target);
                result.setSkillStatusEffect(effect.getSkillStatusEffect());
                result.setValueModifierType(effect.getValueModifierType());
                if(new Random().nextInt(100)>bundle.getAccuracy()){
                    result.setResult((double) 0);
                } else{
                    result.setResult((new Random().nextDouble()*(effect.getMaxValue()-effect.getMinValue()))+effect.getMinValue());
                }
                resultSet.add(result);
            });
        });
    }
}
