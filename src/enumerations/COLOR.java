package enumerations;
public enum COLOR {
    BLUE,GREEN,ORANGE,PURPLE,RED,YELLOW;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
