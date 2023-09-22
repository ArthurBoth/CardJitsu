
package player;

import java.util.*;

import enumerations.*;

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
        for (int i=0; i<5; i++) {
            if (hand[i] != null) {
                deck.add(hand[i]);
            }
            hand[i] = deck.poll();
        }
        shuffleDeck();
    }

    private void shuffleDeck() {
        List<Card> list = new ArrayList<>(deck);
        Collections.shuffle(list);
        deck = new LinkedList<>(list);
    }

    public Card playCard(int cardIndex) {
        Card playedCard = hand[cardIndex];
        hand[cardIndex] = deck.poll();
        return playedCard;
    }

    public void discardCard(Card card) {
        deck.add(card);
    }

    public Score getScore() {
        return score;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setDeck(Queue<Card> deck) {
        this.deck = deck;
        shuffleDeck();
    }

    public void generateDeck() {
        this.deck =  new LinkedList<>();

        for (int i=0;i<20;i++) {
            deck.add(makeCard());
        }
    }
    
    private Card makeCard() {
        int randomElement = random.nextInt(3);
        int randomNumber = random.nextInt(2,13);
        int randomColor = random.nextInt(6);
        int randomEffect = random.nextInt(18);

        ELEMENT element;
        COLOR color;
        EFFECTTYPE effect;

        switch (randomElement) {
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

        switch (randomColor) {
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

        if (randomNumber > 8) {
            switch(randomEffect) {
            case 0:
                effect = EFFECTTYPE.POWER_REVERSAL;
                break;
            case 1:
                effect = EFFECTTYPE.NUMBER_MODIFIER_SELF;
                break;
            case 2:
                effect = EFFECTTYPE.NUMBER_MODIFIER_OTHER;
                break;
            case 3:
                effect = EFFECTTYPE.FIRE_REMOVAL;
                break;
            case 4:
                effect = EFFECTTYPE.WATER_REMOVAL;
                break;
            case 5:
                effect = EFFECTTYPE.SNOW_REMOVAL;
                break;
            case 6:
                effect = EFFECTTYPE.BLUE_REMOVAL;
                break;
            case 7:
                effect = EFFECTTYPE.RED_REMOVAL;
                break;
            case 8:
                effect = EFFECTTYPE.GREEN_REMOVAL;
                break;
            case 9:
                effect = EFFECTTYPE.YELLOW_REMOVAL;
                break;
            case 10:
                effect = EFFECTTYPE.ORANGE_REMOVAL;
                break;
            case 11:
                effect = EFFECTTYPE.PURPLE_REMOVAL;
                break;
            case 12:
                effect = EFFECTTYPE.CHANGE_FIRE_TO_SNOW;
                break;
            case 13:
                effect = EFFECTTYPE.CHANGE_WATER_TO_FIRE;
                break;
            case 14:
                effect = EFFECTTYPE.CHANGE_SNOW_TO_WATER;
                break;
            case 15:
                effect = EFFECTTYPE.BLOCK_FIRE;
                break;
            case 16:
                effect = EFFECTTYPE.BLOCK_WATER;
                break;
            default:
                effect = EFFECTTYPE.BLOCK_SNOW;
            }
            return new Card(element, randomNumber, color, effect);
        }
        return new Card(element,randomNumber,color);
    }
}