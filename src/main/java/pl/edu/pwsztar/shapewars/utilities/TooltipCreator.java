package pl.edu.pwsztar.shapewars.utilities;

import pl.edu.pwsztar.shapewars.entities.dto.SkillDto;
import pl.edu.pwsztar.shapewars.entities.enums.SkillEffect;
import pl.edu.pwsztar.shapewars.entities.enums.TargetType;
import pl.edu.pwsztar.shapewars.entities.enums.ValueModifierType;

import java.util.Arrays;
import java.util.List;

public class TooltipCreator {

    private static final List<SkillEffect> THREE_TURN_EFFECTS=Arrays.asList(
            SkillEffect.ARMOR_MINUS,
            SkillEffect.ARMOR_PLUS,
            SkillEffect.SPEED_MINUS,
            SkillEffect.SPEED_PLUS,
            SkillEffect.STRENGTH_MINUS,
            SkillEffect.STRENGTH_PLUS
    );
    private static final List<SkillEffect> BENEFICIAL=Arrays.asList(
            SkillEffect.ARMOR_PLUS,
            SkillEffect.SPEED_PLUS,
            SkillEffect.STRENGTH_PLUS
    );
    private static final List<SkillEffect> HARMFUL=Arrays.asList(
            SkillEffect.ARMOR_MINUS,
            SkillEffect.SPEED_MINUS,
            SkillEffect.STRENGTH_MINUS
    );
    private static final List<SkillEffect> HITPOINT_EFFECTS=Arrays.asList(
            SkillEffect.DEAL_DAMAGE,
            SkillEffect.RESTORE_HEALTH
    );
    private static final List<SkillEffect> TURN_BASED_EFFECTS=Arrays.asList(
            SkillEffect.STUN
    );
    private static String prefix;
    private static String pending;
    private static String suffix;
    public static String createTooltip(SkillDto dto){
        prefix="";
        pending="";
        suffix="";
        SkillEffect skillEffect = SkillEffect.valueOf(dto.getSkillEffect());
        TargetType targetType = TargetType.valueOf(dto.getTargetType());
        if(THREE_TURN_EFFECTS.contains(skillEffect)) {
            return evaluateThreeTurnStatusEffects(skillEffect, targetType, dto) + getOtherInfo(dto);
        }else if(HITPOINT_EFFECTS.contains(skillEffect)) {
            return evaluateHitPointChangeEffects(skillEffect,targetType,dto) + getOtherInfo(dto);
        }else if(TURN_BASED_EFFECTS.contains(skillEffect)){
            return evaluateTurnBasedEffects(skillEffect,targetType,dto) + getOtherInfo(dto);
        }
        return prefix+suffix;
    }

    private static String evaluateThreeTurnStatusEffects(SkillEffect skillEffect, TargetType targetType, SkillDto dto) {
        if (targetType == TargetType.SINGLE_TARGET_ENEMY) {
            pending += " target enemy's ";
        } else if (targetType == TargetType.SINGLE_TARGET_ALLY) {
            pending += " target ally's ";
        } else if (targetType == TargetType.SELF) {
            pending += " your own ";
        } else if (targetType == TargetType.RANDOM_ALLY) {
            pending += " random ally's ";
        } else if (targetType == TargetType.RANDOM_ENEMY) {
            pending += " random enemy's ";
        } else if (targetType == TargetType.ALL_ALLY) {
            pending += " all allied units' ";
        } else if (targetType == TargetType.ALL_ENEMY) {
            pending += " all enemy units' ";
        } else if (targetType == TargetType.ALL) {
            pending += " all units' ";
        }
        pending += dto.getSkillEffect().replaceAll("_.*", "").toLowerCase();
        suffix += " by " + dto.getMinValue() + "-" + dto.getMaxValue() + " for three turns.";

        if (HARMFUL.contains(skillEffect)) {
            prefix += "Reduce";
        } else if (BENEFICIAL.contains(skillEffect)) {
            prefix += "Increase";
        }
        return prefix+pending+suffix;
    }
    private static String getOtherInfo(SkillDto dto){
        return "\nChance to hit: "+dto.getAccuracy()+"%.\nEnergy cost: "+dto.getCost()+".";
    }

    private static String evaluateHitPointChangeEffects(SkillEffect skillEffect, TargetType targetType, SkillDto dto){
        ValueModifierType valueModifierType = ValueModifierType.valueOf(dto.getValueModifierType());
        if(skillEffect==SkillEffect.DEAL_DAMAGE){
            prefix+="Deal";
            suffix+="damage to";
        } else if(skillEffect==SkillEffect.RESTORE_HEALTH){
            prefix+="Restore";
            suffix+="health to";
        }
        if(valueModifierType!=ValueModifierType.FLAT_VALUE){
            prefix+=" "+dto.getMinValue()+"% - "+dto.getMaxValue()+"% of";
            if(valueModifierType==ValueModifierType.STR_BASED){
                prefix+=" strength as ";
            } else if(valueModifierType==ValueModifierType.TARGET_CURRENT_HP_BASED){
                prefix+=" target's current health as ";
            } else if(valueModifierType==ValueModifierType.SELF_CURRENT_HP_BASED){
                prefix+=" your current health as ";
            }
        } else{
            prefix+=" "+dto.getMinValue()+" - "+dto.getMaxValue();
        }
        if (targetType == TargetType.SINGLE_TARGET_ENEMY) {
            suffix += " a target enemy.";
        } else if (targetType == TargetType.SINGLE_TARGET_ALLY) {
            suffix += " a target ally.";
        } else if (targetType == TargetType.SELF) {
            suffix += " yourself.";
        } else if (targetType == TargetType.RANDOM_ALLY) {
            suffix += " a random ally.";
        } else if (targetType == TargetType.RANDOM_ENEMY) {
            suffix += " a random enemy.";
        } else if (targetType == TargetType.ALL_ALLY) {
            suffix += " all allied units.";
        } else if (targetType == TargetType.ALL_ENEMY) {
            suffix += " all enemy units.";
        } else if (targetType == TargetType.ALL) {
            suffix += " all units.";
        }
        return prefix+pending+suffix;
    }

    private static String evaluateTurnBasedEffects(SkillEffect skillEffect, TargetType targetType, SkillDto dto){
        if(skillEffect==SkillEffect.STUN){
            prefix+="Apply stun to";
        }
        if (targetType == TargetType.SINGLE_TARGET_ENEMY) {
            prefix += " a target enemy ";
        } else if (targetType == TargetType.SINGLE_TARGET_ALLY) {
            prefix += " a target ally ";
        } else if (targetType == TargetType.SELF) {
            prefix += " yourself ";
        } else if (targetType == TargetType.RANDOM_ALLY) {
            prefix += " a random ally ";
        } else if (targetType == TargetType.RANDOM_ENEMY) {
            prefix += " a random enemy.";
        } else if (targetType == TargetType.ALL_ALLY) {
            prefix += " all allied units ";
        } else if (targetType == TargetType.ALL_ENEMY) {
            prefix += " all enemy units ";
        } else if (targetType == TargetType.ALL) {
            prefix += " all units ";
        }
        suffix += " for " + Math.floor(dto.getMinValue()) + "-" + Math.floor(dto.getMaxValue()) + " turns " +
                "(rolled between values: "+dto.getMinValue()+" - "+dto.getMaxValue()+").";
        return prefix+pending+suffix;
    }

}
