package enumerations;

public enum EFFECTTYPE {
    NO_EFFECT,
    POWER_REVERSAL,NUMBER_MODIFIER_SELF,NUMBER_MODIFIER_OTHER,
    FIRE_REMOVAL,WATER_REMOVAL,SNOW_REMOVAL,
    BLUE_REMOVAL,RED_REMOVAL,GREEN_REMOVAL,YELLOW_REMOVAL,ORANGE_REMOVAL,PURPLE_REMOVAL,
    CHANGE_FIRE_TO_SNOW,CHANGE_WATER_TO_FIRE,CHANGE_SNOW_TO_WATER,
    BLOCK_FIRE,BLOCK_WATER,BLOCK_SNOW;

    @Override
    public String toString() {
        String formattedName = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return (formattedName.replace("_", " "));
    }
}