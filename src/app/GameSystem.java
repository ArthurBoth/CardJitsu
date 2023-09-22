package app;

import java.util.*;

import player.*;
import enumerations.*;

public class GameSystem { // TODO update Class Diagram
    private String playerDeckFileName = "playerDeck.csv";
    private String playerBotDeckFileName = "playerDeck.csv";
    
    private Player humanPlayer;
    private PlayerBot botPlayer;
    private ELEMENT elementBlock = null;

    public GameSystem() {
        this.humanPlayer = new Player();
        this.botPlayer = new PlayerBot();
    }

    public void play() {
        setup();
        while (!finishGame()) {
            ComputerInteraction.printMenu(humanPlayer, botPlayer, elementBlock);
            cardBattle(ComputerInteraction.playerInput());
            elementBlock = null;
        }
    }

    private void setup() {
        try {
            humanPlayer.setDeck(ComputerInteraction.readCSV(playerDeckFileName));
            botPlayer.setDeck(ComputerInteraction.readCSV(playerBotDeckFileName));
        } catch (Exception e) {
            humanPlayer.generateDeck();
            botPlayer.generateDeck();
        }
        
        humanPlayer.newHand();
        botPlayer.newHand();
    }

    private void cardBattle(int playerInput) {
        int battleResult;
        Card playerCard = humanPlayer.playCard(playerInput-1);
        Card botCard = botPlayer.playCard();
        
        ComputerInteraction.printCardBattle(playerCard, botCard);

        battleResult = Battle.battle(playerCard, botCard);
        applyEffects(Battle.getPostBattleEffects());

        ComputerInteraction.printBattleResults(battleResult);

        switch(battleResult){
            case 1: // human wins
                humanPlayer.getScore().addToScore(playerCard);
                botPlayer.discardCard(botCard);
                break;
            case -1: // bot wins
                botPlayer.getScore().addToScore(botCard);
                humanPlayer.discardCard(playerCard);
                break;
            default: // draw
                humanPlayer.discardCard(playerCard);
                botPlayer.discardCard(botCard);
        }
    }

    private void applyEffects(Queue<PowerEffect> effects) {
        PowerEffect power;
        while (!effects.isEmpty()) {
            power = effects.poll();
            switch (power.effect) {
                case BLOCK_FIRE:
                    elementBlock = ELEMENT.FIRE;
                    break;
                case BLOCK_WATER:
                    elementBlock = ELEMENT.WATER;
                    break;
                case BLOCK_SNOW:
                    elementBlock = ELEMENT.SNOW;
                    break;
                default:
                    applyScoreRemoval(power);
            }
        }
    }

    private void applyScoreRemoval(PowerEffect power) {
        if (power.getPlayerID() == 1){
            removeScore(power, botPlayer);
            return;
        }
        removeScore(power, humanPlayer);
    }

    private void removeScore(PowerEffect power, Player player) {
        switch (power.effect) {
            case FIRE_REMOVAL:
                player.getScore().removeFromScore(ELEMENT.FIRE);
                return;
            case WATER_REMOVAL:
                player.getScore().removeFromScore(ELEMENT.WATER);
                return;
            case SNOW_REMOVAL:
                player.getScore().removeFromScore(ELEMENT.SNOW);
                return;
            case BLUE_REMOVAL:
                player.getScore().removeFromScore(COLOR.BLUE);
                return;
            case RED_REMOVAL:
                player.getScore().removeFromScore(COLOR.RED);
                return;
            case GREEN_REMOVAL:
                player.getScore().removeFromScore(COLOR.GREEN);
                return;
            case YELLOW_REMOVAL:
                player.getScore().removeFromScore(COLOR.YELLOW);
                return;
            case ORANGE_REMOVAL:
                player.getScore().removeFromScore(COLOR.ORANGE);
                return;
            case PURPLE_REMOVAL:
                player.getScore().removeFromScore(COLOR.PURPLE);
                return;
            default:
        }
    }

    private boolean finishGame() {
        if (humanPlayer.getScore().hasWon()) {
            ComputerInteraction.printVictoryMessage(humanPlayer, botPlayer);
            return true;
        }

        if (botPlayer.getScore().hasWon()) {
            ComputerInteraction.printDefeatMessage(humanPlayer, botPlayer);
            return true;
        }

        return false;
    }
}