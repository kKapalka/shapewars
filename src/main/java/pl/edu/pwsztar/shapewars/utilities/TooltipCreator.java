package pl.edu.pwsztar.shapewars.utilities;

import pl.edu.pwsztar.shapewars.entities.Skill;
import pl.edu.pwsztar.shapewars.entities.SkillEffect;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;
import pl.edu.pwsztar.shapewars.entities.enums.SkillStatusEffect;
import pl.edu.pwsztar.shapewars.entities.enums.TargetType;
import pl.edu.pwsztar.shapewars.entities.enums.ValueModifierType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TooltipCreator {

    private static String prefix;
    public static String createTooltip(Skill skill){
        String tooltip = String.join("\n",skill.getSkillEffectBundles().stream()
                .map(TooltipCreator::processBundle).toArray(String[]::new))
                + "\nCost: "+skill.getCost();
        return tooltip;
    }

    private static String processBundle(SkillEffectBundle bundle){
        Map<SkillStatusEffect, List<SkillEffect>> stringSkillEffectMap = bundle.getSkillEffects().
                stream().collect(Collectors.groupingBy(SkillEffect::getSkillStatusEffect));
        List<String> processedBundleTooltip=new ArrayList<>();
        stringSkillEffectMap.forEach((key,value)->processedBundleTooltip.add(processGroup(key,value)));
        return String.join(". ",processedBundleTooltip) + " ("+bundle.getAccuracy()+"% chance to hit).";
    }
    private static String processGroup(SkillStatusEffect effect, List<SkillEffect> skillEffects){
        String prefixAddition="",delimiter="",suffix="";
        if(ClassifiedEffectsList.THREE_TURN_EFFECTS.contains(effect)) {
            prefixAddition=" of ";
            delimiter=" and ";
            suffix=" for three turns";
        }else if(ClassifiedEffectsList.HITPOINT_EFFECTS.contains(effect)) {
            prefixAddition=" to ";
            delimiter=" and to ";
        }else if(ClassifiedEffectsList.TURN_BASED_EFFECTS.contains(effect)){
            prefixAddition=" ";
            delimiter=" and ";
        } else{
            return "SkillStatusEffect not supported";
        }
        prefix = effect.name().toUpperCase().charAt(0)+effect.name().replaceAll("_", " ").toLowerCase().substring(1) + prefixAddition;
        Map<TargetType, List<SkillEffect>> targetSkillEffectMap = skillEffects.stream().collect(Collectors.groupingBy(SkillEffect::getTargetType));
        List<String> processedBundleTooltip=new ArrayList<>();
        targetSkillEffectMap.forEach((key,value)->processedBundleTooltip.add(sortByTargets(key,value,effect)));
        return prefix+ String.join(delimiter,processedBundleTooltip) + suffix;
    }
    private static String sortByTargets(TargetType type, List<SkillEffect> effects, SkillStatusEffect status){
        List<String> values = new ArrayList<>();
        if(ClassifiedEffectsList.THREE_TURN_EFFECTS.contains(status)) {
            effects.forEach(effect -> {
                String value = type.name().toLowerCase().replace("_", " ") + " by " + effect.getMinValue() + " - " + effect.getMaxValue();
                values.add(value);
            });
        } else if(ClassifiedEffectsList.HITPOINT_EFFECTS.contains(status)){
            String temp = type.name().toLowerCase().replace("_"," ")+" equal to ";
            List<String> tempValues = new ArrayList<>();
            effects.forEach(effect->{
                String value=effect.getMinValue()+" - "+effect.getMaxValue();
                if(effect.getValueModifierType()==ValueModifierType.SELF_CURRENT_HP_BASED){
                    value+="% of this unit's current health";
                } else if(effect.getValueModifierType()==ValueModifierType.TARGET_CURRENT_HP_BASED){
                    value+="% of target's current health";
                } else if(effect.getValueModifierType()==ValueModifierType.STR_BASED){
                    value+="% of this unit's strength";
                }
                tempValues.add(value);
            });
            values.add(temp+String.join(" plus ",tempValues));
        } else if(ClassifiedEffectsList.TURN_BASED_EFFECTS.contains(status)){
            effects.forEach(effect -> {
                String value = type.name().toLowerCase().replace("_", " ")
                        + " for " + Math.floor(effect.getMinValue()) + " - " + Math.floor(effect.getMaxValue())+" turns" +
                        "(rolled between: "+effect.getMinValue()+" - "+effect.getMaxValue()+")";
                values.add(value);
            });
        }
        return String.join(" and ",values);
    }
}
