package player;

import enumerations.COLOR;
import enumerations.EFFECT;
import enumerations.ELEMENT;

public class Card implements Comparable<Card>{
    public final ELEMENT element;
    public final int number;
    public final COLOR color;
    public final EFFECT powerEffect;

    public Card(ELEMENT element, int number, COLOR color, EFFECT powerEffect) {
        this.element = element;
        this.number = number;
        this.color = color;
        this.powerEffect = powerEffect;
    }

    @Override
    public int compareTo(Card other){
        if(this.number > other.number){
            return 1;
        }else if(this.number < other.number){
            return -1;
        }else{
            return 0;
        }
    }
}
