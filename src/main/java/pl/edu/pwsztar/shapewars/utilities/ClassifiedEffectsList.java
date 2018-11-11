package pl.edu.pwsztar.shapewars.utilities;

import pl.edu.pwsztar.shapewars.entities.enums.SkillStatusEffect;

import java.util.Arrays;
import java.util.List;

public class ClassifiedEffectsList {

    public static final List<SkillStatusEffect> THREE_TURN_EFFECTS= Arrays.asList(
            SkillStatusEffect.INCREASE_ARMOR,
            SkillStatusEffect.REDUCE_ARMOR,
            SkillStatusEffect.INCREASE_SPEED,
            SkillStatusEffect.REDUCE_SPEED,
            SkillStatusEffect.INCREASE_STRENGTH,
            SkillStatusEffect.REDUCE_SPEED
    );
    public static final List<SkillStatusEffect> HITPOINT_EFFECTS=Arrays.asList(
            SkillStatusEffect.DEAL_DAMAGE,
            SkillStatusEffect.RESTORE_HEALTH
    );
    public static final List<SkillStatusEffect> TURN_BASED_EFFECTS=Arrays.asList(
            SkillStatusEffect.STUN
    );

}
