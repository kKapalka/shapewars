package pl.edu.pwsztar.shapewars.utilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.pwsztar.shapewars.entities.Changelog;
import pl.edu.pwsztar.shapewars.entities.ColorMap;
import pl.edu.pwsztar.shapewars.entities.ExperienceThreshold;
import pl.edu.pwsztar.shapewars.entities.Shape;
import pl.edu.pwsztar.shapewars.entities.Skill;
import pl.edu.pwsztar.shapewars.entities.SkillEffectBundle;
import pl.edu.pwsztar.shapewars.entities.dto.ColorDamageDto;
import pl.edu.pwsztar.shapewars.entities.dto.ColorMapDto;
import pl.edu.pwsztar.shapewars.entities.dto.ExperienceThresholdDto;
import pl.edu.pwsztar.shapewars.entities.dto.ShapeDto;
import pl.edu.pwsztar.shapewars.entities.dto.SkillDto;
import pl.edu.pwsztar.shapewars.entities.dto.SkillEffectBundleDto;

public class ChangelogUtility {

    public static Changelog compute(Object object, Object dto){
        Changelog changelog = new Changelog();
        if(object instanceof ColorMap && dto instanceof ColorMapDto){
            changelog.setChange(getChangeFromColorMap((ColorMap)object,(ColorMapDto)dto));
        } else if(object instanceof ExperienceThreshold && dto instanceof ExperienceThresholdDto){
            changelog.setChange(getChangeFromExperienceThreshold((ExperienceThreshold)object,(ExperienceThresholdDto)dto));
        } else if(object instanceof Shape && dto instanceof ShapeDto){
            changelog.setChange(getChangeFromShape((Shape)object,(ShapeDto)dto));
        } else if(object instanceof Skill && dto instanceof SkillDto){
            changelog.setChange(getChangeFromSkill((Skill)object,(SkillDto)dto));
        }
        changelog.setChangeTime(LocalDateTime.now());
        return changelog;
    }

    private static String getChangeFromColorMap(ColorMap colorMap, ColorMapDto dto){
        String change="";
        List<String> changeSet = new ArrayList<>();
        if(colorMap.getID()==null){
            change+="Added new color: "+dto.getColorName();
        } else{
            change+=colorMap.getColorName()+" - ";
            if(!colorMap.getColorName().equals(dto.getColorName())){
                changeSet.add("renamed to "+dto.getColorName());
            }
            if(!new String(colorMap.getColorMap()).equals(dto.getColorMap())){
                changeSet.add("updated graphics");
            }
            if(!colorMap.getColorDamageList().stream().map(ColorDamageDto::fromEntity).collect(Collectors.toList()).equals(dto.getColorDamageDtoList())){
                changeSet.add("changed % of damage dealt to other colors");
            }
            if(changeSet.size()==0){
                return "";
            }
            change+= String.join(", ",changeSet);
        }
        return change;
    }

    private static String getChangeFromExperienceThreshold(ExperienceThreshold threshold, ExperienceThresholdDto dto){
        String change="";
        List<String> changeSet = new ArrayList<>();
        if(threshold.getID()==null){
            change+="Added new threshold for level "+dto.getLevel()+": "+dto.getThreshold();
        } else{
            change+="Threshold for level "+threshold.getLevel()+" - ";
            if(!threshold.getLevel().equals(dto.getLevel())){
                changeSet.add("now applies to level "+dto.getLevel());
            }
            if(!threshold.getThreshold().equals(dto.getThreshold())){
                changeSet.add("now equals "+dto.getThreshold());
            }
            if(changeSet.size()==0){
                return "";
            }
            change+= String.join(", ",changeSet);
        }
        return change;
    }

    public static String getChangeFromShape(Shape shape, ShapeDto dto){
        String change="";
        List<String> changeSet = new ArrayList<>();
        if(shape.getId()==null){
            change+="Added new shape: "+dto.getName()+". Be sure to see it in fight!";
        } else{
            change+=shape.getName()+" - ";
            if(!shape.getName().equals(dto.getName())){
                changeSet.add("renamed to "+dto.getName());
            }
            if(!new String(shape.getImage()).equals(dto.getImage())){
                changeSet.add("updated graphics");
            }
            if(!Arrays.asList(shape.getBaselineHp(),shape.getHPMinGrowth(),shape.getHPMaxGrowth()).equals(dto.getHpParameters())){
                changeSet.add("changed scaling on hitpoints");
            }
            if(!Arrays.asList(shape.getBaselineArmor(),shape.getARMMinGrowth(),shape.getARMMaxGrowth()).equals(
               dto.getArmParameters())){
                changeSet.add("changed scaling on armor");
            }
            if(!Arrays.asList(shape.getBaselineStrength(),shape.getSTRMinGrowth(),shape.getSTRMaxGrowth()).equals(
               dto.getStrParameters())){
                changeSet.add("changed scaling on strength");
            }
            if(!shape.getBaselineSpeed().equals(dto.getSpeed())){
                changeSet.add("changed speed, from "+shape.getBaselineSpeed()+" to "+dto.getSpeed());
            }
            List<Long> shapeSkillSet = shape.getSkillSet().stream().map(Skill::getID).collect(Collectors.toList());
            if(!shapeSkillSet.equals(dto.getSkillIDset())){
                changeSet.add("changed skillset");
            }
            if(changeSet.size()==0){
                return "";
            }
            change+= String.join(", ",changeSet);
        }
        return change;
    }
    public static String getChangeFromSkill(Skill skill, SkillDto dto){
        String change="";
        List<String> changeSet = new ArrayList<>();
        if(skill.getID()==null){
            change+="Added new skill: "+dto.getName()+". Be sure to see it being used!";
        } else{
            change+=skill.getName()+" - ";
            if(!skill.getName().equals(dto.getName())){
                changeSet.add("renamed to "+dto.getName());
            }
            if(!new String(skill.getIcon()).equals(dto.getIcon())){
                changeSet.add("updated graphics");
            }
            if(!skill.getCost().equals(dto.getCost())){
                changeSet.add("changed cost to "+dto.getCost());
            }
            if(!skill.getSkillEffectBundles().stream().map(SkillEffectBundleDto::fromEntity).collect(Collectors.toList()).equals(dto.getSkillEffectBundles())){
                changeSet.add("has new effect");
            }
            if(changeSet.size()==0){
                return "";
            }
            change+= String.join(", ",changeSet);
        }
        return change;
    }
}
