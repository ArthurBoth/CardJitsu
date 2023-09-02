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
        return null;
    }
}
