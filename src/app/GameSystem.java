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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
       
    public void play() {
        setup();
        while (!finishGame()) {
            ComputerInteraction.printMenu(humanPlayer, botPlayer, elementBlock);
            cardBattle(ComputerInteraction.playerInput());
        }
    }

    private void setup() {
        try {
            humanPlayer.setDeck(ComputerInteraction.readCSV(playerDeckFileName));
            botPlayer.setDeck(ComputerInteraction.readCSV(playerBotDeckFileName));

        } catch (Exception e) {
            ComputerInteraction.printGeneratedDeckMessage(e.getMessage());
            humanPlayer.generateDeck();
            botPlayer.generateDeck();
        }
        
        humanPlayer.newHand();
        botPlayer.newHand();
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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
       
    private void cardBattle(int playerInput) {
        int battleResult;
        Queue<PowerEffect> postBattleEffects;
        Queue<PowerEffect> nextTurnEffects;

        Card playerCard = humanPlayer.playCard(playerInput-1);
        Card botCard = botPlayer.playCard();

        
        battleResult = Battle.battle(playerCard, botCard);
        postBattleEffects = Battle.getPostBattleEffects();
        nextTurnEffects = Battle.getNextTurnEffects();
        
        ComputerInteraction.printCardBattle(playerCard, botCard);
        ComputerInteraction.printBattleResults(battleResult);

        if (!nextTurnEffects.isEmpty()) {
            ComputerInteraction.printNextTurnBattleEffects(nextTurnEffects);
        }

        applyEffects(postBattleEffects);

        switch(battleResult){
            case 1: // human wins
                humanPlayer.getScore().addToScore(playerCard);
                botPlayer.returnToDeck(botCard);
                break;
            case -1: // bot wins
                botPlayer.getScore().addToScore(botCard);
                humanPlayer.returnToDeck(playerCard);
                break;
            default: // draw
                humanPlayer.returnToDeck(playerCard);
                botPlayer.returnToDeck(botCard);
        }
    }

    private void applyEffects(Queue<PowerEffect> effects) {
        PowerEffect power;
        while (!effects.isEmpty()) {
            power = effects.poll();
            switch (power.effect) {
                case BLOCK_FIRE:
                    elementBlock = ELEMENT.FIRE;
                    ComputerInteraction.printThisTurnPostBattleEffects(power);
                    break;
                    case BLOCK_WATER:
                    elementBlock = ELEMENT.WATER;
                    ComputerInteraction.printThisTurnPostBattleEffects(power);
                    break;
                    case BLOCK_SNOW:
                    elementBlock = ELEMENT.SNOW;
                    ComputerInteraction.printThisTurnPostBattleEffects(power);
                    break;
                    default:
                    elementBlock = null;
                    applyScoreRemoval(power);
                    break;
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
            Card card;

            switch (power.effect) {
            case FIRE_REMOVAL:
                card = player.getScore().removeFromScore(ELEMENT.FIRE);
                break;
            case WATER_REMOVAL:
                card = player.getScore().removeFromScore(ELEMENT.WATER);
                break;
            case SNOW_REMOVAL:
                card = player.getScore().removeFromScore(ELEMENT.SNOW);
                break;
            case BLUE_REMOVAL:
                card = player.getScore().removeFromScore(COLOR.BLUE);
                break;
            case RED_REMOVAL:
                card = player.getScore().removeFromScore(COLOR.RED);
                break;
            case GREEN_REMOVAL:
                card = player.getScore().removeFromScore(COLOR.GREEN);
                break;
            case YELLOW_REMOVAL:
                card = player.getScore().removeFromScore(COLOR.YELLOW);
                break;
            case ORANGE_REMOVAL:
                card = player.getScore().removeFromScore(COLOR.ORANGE);
                break;
            case PURPLE_REMOVAL:
                card = player.getScore().removeFromScore(COLOR.PURPLE);
                break;
            default:
                card = null;
                break;
        }
        ComputerInteraction.printThisTurnPostBattleEffects(power, card);
    }
}