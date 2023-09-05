package player;

import java.util.*;

import enumerations.COLOR;
import enumerations.ELEMENT;

public class Player {
    private Random random = new Random();
    private Score score;
    private Card[] hand;
    private Queue<Card> deck;

    public Player() {
        this.score = new Score();
        this.hand = new Card[5];
    }

    public void newHand() {
        for (int i=0;i<5;i++) {
            if (hand[i] != null) deck.add(hand[i]);
            hand[i] = deck.poll();
        }
        shuffleDeck();
    }

    public Card playCard(int cardIndex){
        Card playedCard = hand[cardIndex];
        hand[cardIndex] = deck.poll();
        return playedCard;
    }

    public void discardCard(Card card){
        deck.add(card);
    }

    private void shuffleDeck() {
        List<Card> list = new ArrayList<>(deck);
        Collections.shuffle(list);
        deck = new LinkedList<>(list);
    }

    public Score getScore() {
        return score;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setDeck(Queue<Card> deck){
        this.deck = deck;
        shuffleDeck();
    }

    public void generateDeck() {
        this.deck =  new LinkedList<>();
        for (int i=0;i<20;i++){
            deck.add(makeCard());
        }
    }
    
    private Card makeCard(){
        /* TODO add option to make power cards */
        int randomElement = random.nextInt(2);
        int randomNumber = random.nextInt(2,8);
        int randomColor = random.nextInt(5);

        ELEMENT element;
        COLOR color;

        switch(randomElement){
            case 0:
                element = ELEMENT.FIRE;
                break;
            case 1:
                element = ELEMENT.WATER;
                break;
            default:
                element = ELEMENT.SNOW;
                break;
        }

        switch(randomColor){
            case 0:
                color = COLOR.BLUE;
                break;
            case 1:
                color = COLOR.GREEN;
                break;
            case 2:
                color = COLOR.ORANGE;
                break;
            case 3:
                color = COLOR.PURPLE;
                break;
            case 4:
                color = COLOR.RED;
                break;
            default:
                color = COLOR.YELLOW;
                break;
        }

        return new Card(element,randomNumber,color);
    }
}