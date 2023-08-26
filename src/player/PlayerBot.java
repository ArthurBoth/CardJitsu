package player;

import java.util.*;

public class PlayerBot extends Player {
    private Random random = new Random();
    public PlayerBot() {
        super();
    }

    public void playCard() {
        int choice =(random.nextInt(4)+1);
        super.playCard(choice);
    }
}
