package player;

import java.util.*;

public class PlayerBot extends Player {
    private Random random = new Random();
    public PlayerBot() {
        super();
    }

    public Card playCard() {
        int choice =(random.nextInt(4));
        return super.playCard(choice);
    }
}
