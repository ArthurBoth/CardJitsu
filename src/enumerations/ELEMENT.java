package enumerations;

public enum ELEMENT{
    FIRE,WATER,SNOW;

    public int elementBattle(ELEMENT other){
        if (this == other) return 0;
        switch (this) {
            case FIRE:
                return (other == SNOW) ? 1 : -1;
            case WATER:
                return (other == FIRE) ? 1 : -1;
            case SNOW:
                return (other == WATER) ? 1 : -1;
            default:
                return 0;
        }
    }

    @Override
    public String toString(){
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
