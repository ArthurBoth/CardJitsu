package player;

import java.util.*;
import enumerations.EFFECTTYPE;

public class PowerEffect {
    public final EFFECTTYPE effect;
    private static Set<EFFECTTYPE> currentTurnEffects = new HashSet<>(Arrays.asList(
        EFFECTTYPE.POWER_REVERSAL,
        EFFECTTYPE.FIRE_REMOVAL,EFFECTTYPE.WATER_REMOVAL,EFFECTTYPE.SNOW_REMOVAL,
        EFFECTTYPE.BLUE_REMOVAL,EFFECTTYPE.RED_REMOVAL,EFFECTTYPE.GREEN_REMOVAL,EFFECTTYPE.YELLOW_REMOVAL,
        EFFECTTYPE.ORANGE_REMOVAL,EFFECTTYPE.PURPLE_REMOVAL
        ));
    private static Set<EFFECTTYPE> nextTurnEffects = new HashSet<>(Arrays.asList(
        EFFECTTYPE.NUMBER_MODIFIER_SELF,EFFECTTYPE.NUMBER_MODIFIER_OTHER,
        EFFECTTYPE.CHANGE_FIRE_TO_SNOW,EFFECTTYPE.CHANGE_WATER_TO_FIRE,EFFECTTYPE.CHANGE_SNOW_TO_WATER,
        EFFECTTYPE.BLOCK_FIRE,EFFECTTYPE.BLOCK_WATER,EFFECTTYPE.BLOCK_SNOW
        ));
    private int player;
    
    public PowerEffect(EFFECTTYPE effectType){
        this.effect = effectType;
    }

    public PowerEffect(EFFECTTYPE effectType, int player){
        this.effect = effectType;
        this.player = player;
    }

    public static boolean applyThisTurn(EFFECTTYPE e){
        return currentTurnEffects.contains(e);
    }
}
