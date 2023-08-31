package app;

import player.*;
import java.util.*;
import enumerations.*;

public class GameSystem {
    
    private Player humanPlayer;
    private PlayerBot botPlayer;
    private Battle judge;
    private ELEMENT elementBlock; // TODO implement element block

    public GameSystem(){
        this.humanPlayer = new Player();
        this.botPlayer = new PlayerBot();
        this.judge = new Battle();
    }

    public void play(){
        setup();
        while(!finishGame()){
            menu();
            int input = input();
            cardBattle(input);
        }
    }

    private void setup(){
        humanPlayer.generateDeck();
        botPlayer.generateDeck();

        humanPlayer.newHand();
        botPlayer.newHand();
    }

    private void menu(){
        System.out.println("Opponent's score:");
        System.out.println(botPlayer.getScore().toString());
        System.out.println("------------------------------------------------------------");
        System.out.println("Your score:");
        System.out.println(humanPlayer.getScore().toString());
        System.out.print("\n");
        System.out.println("Your hand:");
        System.out.println("[1] " + humanPlayer.getHand()[0].toString());
        System.out.println("[2] " + humanPlayer.getHand()[1].toString());
        System.out.println("[3] " + humanPlayer.getHand()[2].toString());
        System.out.println("[4] " + humanPlayer.getHand()[3].toString());
        System.out.println("[5] " + humanPlayer.getHand()[4].toString());
        System.out.print("Choose a card to play: ");
    }

    private int input(){
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        int input = -1;

        while(flag){
            flag = false;
            try {
                input = scanner.nextInt();
                if ((input < 1) || (input > 5)){
                    throw new InputMismatchException();
                }
            } catch (Exception e) {
                System.out.print("Invalid input, try again: ");
                    flag = true;
            }
        }
        
        return input;
    }
    
    private void cardBattle(int playerInput){
     /*
     * TODO add fire block effect
     * TODO add water block effect
     * TODO add snow block effect
     */
        int battleResult;
        Card playerCard = humanPlayer.playCard(playerInput-1);
        Card botCard = botPlayer.playCard();
        
        System.out.println("\n");
        System.out.println("-------------------------------------------------------------------------------");

        System.out.println("You played " + playerCard.toString().toLowerCase());
        System.out.println("Your opponent played " + botCard.toString().toLowerCase());

        battleResult = judge.battle(playerCard, botCard);
        applyEffects(judge.getPostBattleEffects());

        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("\n");

        switch(battleResult){
            case 1:
                System.out.println("You won this round!");
                humanPlayer.getScore().addToScore(playerCard);
                botPlayer.discardCard(botCard);
                break;
            case -1:
                System.out.println("You lost this round!");
                botPlayer.getScore().addToScore(botCard);
                humanPlayer.discardCard(playerCard);
                break;
            default:
                System.out.println("Draw!");
                break;
        }
    }

    private void applyEffects(Queue<PowerEffect> effects){
        PowerEffect power;
        while (!effects.isEmpty()){
            power = effects.poll();
            switch (power.effect){
                case BLOCK_FIRE:
                    applyElementBlock(ELEMENT.FIRE);
                    break;
                case BLOCK_WATER:
                    applyElementBlock(ELEMENT.WATER);
                    break;
                case BLOCK_SNOW:
                    applyElementBlock(ELEMENT.SNOW);
                    break;
                default:
                    applyScoreRemoval(power);
            }
        }
    }

    private void applyElementBlock(ELEMENT element){
        elementBlock = element;
    }

    private void applyScoreRemoval(PowerEffect power){
        if (power.getPlayerID() == 1){
            removeScore(power, botPlayer);
        } else {
            removeScore(power, humanPlayer);
        }
    }

    private void removeScore(PowerEffect power, Player player){
        switch (power.effect){
            case FIRE_REMOVAL:
                player.getScore().removeFromScore(ELEMENT.FIRE);
                break;
            case WATER_REMOVAL:
                player.getScore().removeFromScore(ELEMENT.WATER);
                break;
            case SNOW_REMOVAL:
                player.getScore().removeFromScore(ELEMENT.SNOW);
                break;
            case BLUE_REMOVAL:
                player.getScore().removeFromScore(COLOR.BLUE);
                break;
            case RED_REMOVAL:
                player.getScore().removeFromScore(COLOR.RED);
                break;
            case GREEN_REMOVAL:
                player.getScore().removeFromScore(COLOR.GREEN);
                break;
            case YELLOW_REMOVAL:
                player.getScore().removeFromScore(COLOR.YELLOW);
                break;
            case ORANGE_REMOVAL:
                player.getScore().removeFromScore(COLOR.ORANGE);
                break;
            case PURPLE_REMOVAL:
                player.getScore().removeFromScore(COLOR.PURPLE);
                break;
            default:
        }
    }

    private boolean finishGame(){
        if (humanPlayer.getScore().hasWon()){
            System.out.println("You won!");
            return true;
        } else if (botPlayer.getScore().hasWon()){
            System.out.println("You lost!");
            return true;
        }
        return false;
    }
}