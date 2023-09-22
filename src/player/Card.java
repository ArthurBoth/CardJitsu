package player;

import enumerations.COLOR;
import enumerations.EFFECTTYPE;
import enumerations.ELEMENT;

public class Card implements Comparable<Card>{
    public final ELEMENT element;
    public final int number;
    public final COLOR color;
    public final EFFECTTYPE powerEffect;
    
    public Card(ELEMENT element, int number, COLOR color) {
        this.element = element;
        this.number = number;
        this.color = color;
        this.powerEffect = EFFECTTYPE.NO_EFFECT;
    }

    public Card(ELEMENT element, int number, COLOR color, EFFECTTYPE powerEffect) {
        this.element = element;
        this.number = number;
        this.color = color;
        this.powerEffect = powerEffect;
    }

    @Override
    public int compareTo(Card other){
        if(this.number > other.number){
            return 1;
        } else if(this.number < other.number){
            return -1;
        } else{
            return 0;
        }
    }

    @Override
    public String toString(){
        String returnString = "A " + element.toString() + " " + number + ", " + color.toString() + " colored card";
        if (powerEffect == EFFECTTYPE.NO_EFFECT) {
            return returnString + ".";
        }
        return returnString + " with a \u001B[34m" + powerEffect.toString() + " effect\u001B[0m.";
    }
}
