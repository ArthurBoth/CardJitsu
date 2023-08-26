package app;
import enumerations.*;
import player.*;
import java.util.*;

public class GameSystem {
    private Random random = new Random();
    
    private Player humanPlayer;
    private Player botPlayer;
    private EFFECT[] powerCardEffectField;
    private EFFECT[] powerCardEffectPlayer;
    private EFFECT[] powerCardEffectBot;
    private Battle judge;

    public GameSystem(){
        this.humanPlayer = new Player();
        this.botPlayer = new Player();
        this.powerCardEffectField = new EFFECT[2];
        this.powerCardEffectPlayer = new EFFECT[2];
        this.powerCardEffectBot = new EFFECT[2];
        this.judge = new Battle();
    }

    //TODO
    public void play(){
        setup();
    }

    private void setup(){
        LinkedList<Card> deckPlayer1 = new LinkedList<>();
        LinkedList<Card> deckPlayer2 = new LinkedList<>();

        for (int i=0;i<20;i++){
            deckPlayer1.add(makeCard());
            deckPlayer2.add(makeCard());
        }

        humanPlayer.fillDeck(deckPlayer1);
        botPlayer.fillDeck(deckPlayer2);

        humanPlayer.newHand();
        botPlayer.newHand();
    }

    /* TODO add option to make power cards */
    private Card makeCard(){
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

    //TODO
    private void menu(){}

    //TODO
    private void drawPhase(){}

    //TODO
    private void cardBattle(){}

    //TODO
    private void finishGame(){}
}