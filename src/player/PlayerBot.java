package player;

import java.util.*;
import enumerations.ELEMENT;

public class PlayerBot extends Player {
    private Random random = new Random();
    public PlayerBot() {
        super();
    }

    public Card playCard() {
        int choice =(random.nextInt(4));
        return super.playCard(choice);
    }

    // plays a card that is not blocked by the given element
    public Card playCard(ELEMENT blockedElement){
        //TODO
        Card[] hand = super.getHand();
        boolean[] checkHand = new boolean[5];
        ArrayList<Card> availableCards = new ArrayList<>();

        for (int i = 0; i<5; i++){
            if (hand[i].element == blockedElement){
                checkHand[i] = true;
            } else {
                checkHand[i] = false;
                availableCards.add(hand[i]);
            }
        }

        if (!availableCards.isEmpty()){
            if (availableCards.size() == 5){
                super.newHand();
                return playCard();
            }
        }
        
        int choice = random.nextInt(availableCards.size()-1);
        return super.playCard(choice);
    }
}
