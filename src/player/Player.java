package player;

import java.util.*;

public class Player {
    private Score score;
    private Card[] hand;
    private Queue<Card> deck;

    public Player() {
        this.score = new Score();
        this.hand = new Card[5];
    }

    public void fillDeck(List<Card> deck) {
        this.deck = new LinkedList<>(deck);
    }

    public void newHand() {
        for (int i=0;i<5;i++) {
            if (hand[i] != null) deck.add(hand[i]);
            hand[i] = deck.poll();
        }
        shuffleDeck();
    }

    public void playCard(int cardIndex){
        deck.add(hand[cardIndex]);
        hand[cardIndex] = deck.poll();
        shuffleDeck();
    }

    private void shuffleDeck() {
        List<Card> list = new ArrayList<>(deck);
        Collections.shuffle(list);
        deck = new LinkedList<>(list);
    }

    public Score getScore() {
        return score;
    }
}