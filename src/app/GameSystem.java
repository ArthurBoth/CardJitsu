package app;
import enumerations.*;
import player.*;

public class GameSystem {
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
        
    }

    //TODO
    private void setup(){}

    //TODO
    private void menu(){}

    //TODO
    private void drawPhase(){}

    //TODO
    private void cardBattle(){}

    //TODO
    private void finishGame(){}
}