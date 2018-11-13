package pl.edu.pwsztar.shapewars.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import pl.edu.pwsztar.shapewars.entities.Action;
import pl.edu.pwsztar.shapewars.entities.Fighter;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;
import pl.edu.pwsztar.shapewars.entities.TargetStatus;
import pl.edu.pwsztar.shapewars.entities.User;
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

        action.getSkill().getSkillEffectBundles().forEach(SkillEvaluator::processBundle);
        return new ArrayList<>();
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
            eligibleTargets.forEach(target->{
                TargetStatus newTargetStatus=new TargetStatus();
                //TODO dodawanie statusów
            });
        });
    }
}
