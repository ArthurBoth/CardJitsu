package player;

import java.util.*;
import java.util.Map.Entry;

import enumerations.*;

public class Score {
    private ArrayList<Card> playerScore;

    public Score() {
        this.playerScore = new ArrayList<>();
    }

    public void addToScore(Card card) {
        playerScore.add(card);
    }

    public void removeColorFromScore(COLOR color) {
        for (Card c : playerScore) {
            if (c.color == color) {
                playerScore.remove(c);
                return;
            }
        }
    }

    public void removeElementFromScore(ELEMENT element) {
        for (Card c : playerScore) {
            if (c.element == element) {
                playerScore.remove(c);
                return;
            }
        }
    }

    public boolean hasWon(){
        return (winBySingleElement() || winByAllElements());
    }
    
    private boolean winBySingleElement(){
        boolean flagFire = checkSingleElement(ELEMENT.FIRE);
        boolean flagWater = checkSingleElement(ELEMENT.WATER);
        boolean flagSnow = checkSingleElement(ELEMENT.SNOW);

        return (flagFire || flagWater || flagSnow);
    }

    private boolean checkSingleElement(ELEMENT element){
        HashSet<COLOR> colors = new HashSet<>();

        for (Card c : playerScore) {
            if (c.element == element) {
                colors.add(c.color);
            }
        }

        return (colors.size() >= 3);
    }

    private boolean winByAllElements(){
        HashSet<COLOR> fireColors = new HashSet<>();
        HashSet<COLOR> waterColors = new HashSet<>();
        HashSet<COLOR> snowColors = new HashSet<>();
         
        for(Card c : playerScore){
            if(c.element == ELEMENT.FIRE){
                fireColors.add(c.color);
            }
            else if(c.element == ELEMENT.WATER){
                waterColors.add(c.color);
            }
            else{
                snowColors.add(c.color);
            }
        }

        for (COLOR color : fireColors) {
            if(checkDifferentColors(color, waterColors, snowColors)){
                return true;
            }
        }
        
        return false;
    }

    /*
     * Returns true if 'waterColors' and 'snowColors' have at least one color different from each other and from 'color'
     */
    private boolean checkDifferentColors(COLOR color, HashSet<COLOR> element1, HashSet<COLOR> element2){
        HashSet<COLOR> elem1 = new HashSet<>(element1);
        HashSet<COLOR> elem2 = new HashSet<>(element2);

        elem1.remove(color);
        elem2.remove(color);

        for(COLOR c : elem1){
            if(!elem2.contains(c)){
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        HashMap<COLOR,Integer> fireColors = new HashMap<>();
        HashMap<COLOR,Integer> waterColors = new HashMap<>();
        HashMap<COLOR,Integer> snowColors = new HashMap<>();
         
        for(Card c : playerScore){
            if(c.element == ELEMENT.FIRE){
                fireColors.merge(c.color, 1, Integer::sum);
            }
            else if(c.element == ELEMENT.WATER){
                waterColors.merge(c.color, 1, Integer::sum);
            }
            else{
                snowColors.merge(c.color, 1, Integer::sum);
            }
        }


        StringBuilder str = new StringBuilder("Fire cards:\n");
        for (Entry<COLOR, Integer> entry : fireColors.entrySet()) {
            str.append("\t" + entry.getValue() + " " + entry.getKey().name() + " fire card");
            if (entry.getValue() != 1) str.append("s");
            str.append(".\n");
        }
        str.append("Water cards:\n");
        for (Entry<COLOR, Integer> entry : waterColors.entrySet()) {
            str.append("\t" + entry.getValue() + " " + entry.getKey().name() + " water card");
            if (entry.getValue() != 1) str.append("s");
            str.append(".\n");
        }
        str.append("Snow cards:\n");
        for (Entry<COLOR, Integer> entry : snowColors.entrySet()) {
            str.append("\t" + entry.getValue() + " " + entry.getKey().name() + " snow card");
            if (entry.getValue() != 1) str.append("s");
            str.append(".\n");
        }
        return str.toString();
    }
}
