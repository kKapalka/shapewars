package pl.edu.pwsztar.shapewars.utilities;

import pl.edu.pwsztar.shapewars.entities.enums.SkillStatusEffect;

import java.util.Arrays;
import java.util.List;

public class ClassifiedEffectsList {

    public static final List<SkillStatusEffect> THREE_TURN_EFFECTS= Arrays.asList(
            SkillStatusEffect.ARMOR_MINUS,
            SkillStatusEffect.ARMOR_PLUS,
            SkillStatusEffect.SPEED_MINUS,
            SkillStatusEffect.SPEED_PLUS,
            SkillStatusEffect.STRENGTH_MINUS,
            SkillStatusEffect.STRENGTH_PLUS
    );
    public static final List<SkillStatusEffect> BENEFICIAL=Arrays.asList(
            SkillStatusEffect.ARMOR_PLUS,
            SkillStatusEffect.SPEED_PLUS,
            SkillStatusEffect.STRENGTH_PLUS
    );
    public static final List<SkillStatusEffect> HARMFUL=Arrays.asList(
            SkillStatusEffect.ARMOR_MINUS,
            SkillStatusEffect.SPEED_MINUS,
            SkillStatusEffect.STRENGTH_MINUS
    );
    private static final List<SkillStatusEffect> HITPOINT_EFFECTS=Arrays.asList(
            SkillStatusEffect.DEAL_DAMAGE,
            SkillStatusEffect.RESTORE_HEALTH
    );
    public static final List<SkillStatusEffect> TURN_BASED_EFFECTS=Arrays.asList(
            SkillStatusEffect.STUN
    );

}
