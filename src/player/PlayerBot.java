package player;

import java.util.*;

import enumerations.ELEMENT;

public class PlayerBot extends Player {
    private Random random = new Random();
    
    public PlayerBot() {
        super();
    }

    public Card playCard() {
        int choice = random.nextInt(4);
        return super.playCard(choice);
    }

    public Card playCard(ELEMENT blockedElement){
        int choice;
        Card[] hand = super.getHand();
        ArrayList<Card> availableCards = new ArrayList<>();

        for (int i = 0; i<5; i++){
            if (hand[i].element != blockedElement){
                availableCards.add(hand[i]);
            }
        }

        if (availableCards.isEmpty()){
            super.newHand();
            return playCard();
            
        }

        choice = random.nextInt(availableCards.size()-1);

        return super.playCard(choice);
    }
}
