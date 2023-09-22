package player;

import java.util.*;
import enumerations.EFFECTTYPE;

public class PowerEffect {
    public final EFFECTTYPE effect;
    private static Set<EFFECTTYPE> nextTurnEffects = new HashSet<>(Arrays.asList(
        EFFECTTYPE.POWER_REVERSAL,EFFECTTYPE.NUMBER_MODIFIER_SELF,EFFECTTYPE.NUMBER_MODIFIER_OTHER,
        EFFECTTYPE.CHANGE_FIRE_TO_SNOW,EFFECTTYPE.CHANGE_WATER_TO_FIRE,EFFECTTYPE.CHANGE_SNOW_TO_WATER
        ));
        
    private static Set<EFFECTTYPE> outOfBattleEffects = new HashSet<>(Arrays.asList(
        EFFECTTYPE.FIRE_REMOVAL,EFFECTTYPE.WATER_REMOVAL,EFFECTTYPE.SNOW_REMOVAL,
        EFFECTTYPE.BLUE_REMOVAL,EFFECTTYPE.RED_REMOVAL,EFFECTTYPE.GREEN_REMOVAL,EFFECTTYPE.YELLOW_REMOVAL,
        EFFECTTYPE.ORANGE_REMOVAL,EFFECTTYPE.PURPLE_REMOVAL,
        EFFECTTYPE.BLOCK_FIRE,EFFECTTYPE.BLOCK_WATER,EFFECTTYPE.BLOCK_SNOW
        )); // These are effects that must be applied in GameSystem.java, not Battle.java
    
    private int playerID; // refers to the player who played the card that caused the effect
    
    public PowerEffect(EFFECTTYPE effectType) {
        this.effect = effectType;
    }

    public PowerEffect(EFFECTTYPE effectType, int playerID) {
        this.effect = effectType;
        this.playerID = playerID;
    }

    public static boolean applyNextTurn(EFFECTTYPE e) {
        return nextTurnEffects.contains(e);
    }

    public static boolean applyOutOfBattle(EFFECTTYPE e) {
        return outOfBattleEffects.contains(e);
    }

    public int getPlayerID() {
        return playerID;
    }
}
