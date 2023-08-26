package player;

import java.util.*;

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
}
