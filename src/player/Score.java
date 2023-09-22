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

    public Card removeFromScore(COLOR color) {
        for (Card c : playerScore) {
            if (c.color == color) {
                playerScore.remove(c);
                return c;
            }
        }
        return null;
    }

    public Card removeFromScore(ELEMENT element) {
        for (Card c : playerScore) {
            if (c.element == element) {
                playerScore.remove(c);
                return c;
            }
        }
        return null;
    }

    public boolean hasWon() {
        return (winBySingleElement() || winByAllElements());
    }
    
    private boolean winBySingleElement() {
        boolean wonByFire = checkSingleElement(ELEMENT.FIRE);
        boolean wonByWater = checkSingleElement(ELEMENT.WATER);
        boolean wonBySnow = checkSingleElement(ELEMENT.SNOW);

        return (wonByFire || wonByWater || wonBySnow);
    }

    private boolean checkSingleElement(ELEMENT element) {
        HashSet<COLOR> colors = new HashSet<>();

        for (Card c : playerScore) {
            if (c.element == element) {
                colors.add(c.color);
            }
        }

        return (colors.size() > 2);
    }

    private boolean winByAllElements() {
        HashSet<COLOR> fireColors = new HashSet<>();
        HashSet<COLOR> waterColors = new HashSet<>();
        HashSet<COLOR> snowColors = new HashSet<>();
         
        for (Card c : playerScore) {
            switch (c.element) {
                case FIRE:
                    fireColors.add(c.color);
                    break;
                case WATER:
                    waterColors.add(c.color);
                    break;
                default:
                    snowColors.add(c.color);
            }
        }

        for (COLOR color : fireColors) {
            if (checkDifferentColors(color, waterColors, snowColors)) {
                return true;
            }
        }
        
        return false;
    }

    /*
     * Returns true if 'element1' and 'element2' have at least one color different from each other and from 'color'
     */
    private boolean checkDifferentColors(COLOR color, HashSet<COLOR> element1, HashSet<COLOR> element2) {
        HashSet<COLOR> elem1 = new HashSet<>(element1);
        HashSet<COLOR> elem2 = new HashSet<>(element2);

        elem1.remove(color);
        elem2.remove(color);

        if (elem1.isEmpty() || elem2.isEmpty()) {
            return false;
        }

        for (COLOR c : elem1) {
            if (!elem2.contains(c)) {
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

        StringBuilder str;
         
        for (Card c : playerScore) {
            switch (c.element) {
                case FIRE:
                    fireColors.merge(c.color, 1, Integer::sum);
                    break;
                case WATER:
                    waterColors.merge(c.color, 1, Integer::sum);
                    break;
                default:
                    snowColors.merge(c.color, 1, Integer::sum);
            }
        }


        str = new StringBuilder("Fire cards:\n");
        for (Entry<COLOR, Integer> count : fireColors.entrySet()) {
            str.append("\t" + count.getValue() + " " + count.getKey().toString() + " fire card");
            if (count.getValue() != 1) {
                str.append("s");
            }
            str.append(".\n");
        }
        str.append("Water cards:\n");
        for (Entry<COLOR, Integer> count : waterColors.entrySet()) {
            str.append("\t" + count.getValue() + " " + count.getKey().toString() + " water card");
            if (count.getValue() != 1) {
                str.append("s");
            } 
            str.append(".\n");
        }
        str.append("Snow cards:\n");
        for (Entry<COLOR, Integer> count : snowColors.entrySet()) {
            str.append("\t" + count.getValue() + " " + count.getKey().toString() + " snow card");
            if (count.getValue() != 1) {
                str.append("s");
            } 
            str.append(".\n");
        }
        return str.toString();
    }
}
