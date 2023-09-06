package app;

import player.*;
import java.util.*;
import enumerations.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class GameSystem { // TODO update Class Diagram
    
    private Player humanPlayer;
    private PlayerBot botPlayer;
    private Battle judge;
    private ELEMENT elementBlock = null;
    private int inputLimit = 5;

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
        try {
            humanPlayer.setDeck(readCSV("playerDeck.csv"));
            botPlayer.setDeck(readCSV("playerDeck.csv"));
        } catch (IOException e) {
            humanPlayer.generateDeck();
            botPlayer.generateDeck();
        }

        humanPlayer.newHand();
        botPlayer.newHand();
    }

    private void menu(){
        // TODO display effects
        printScores();
        System.out.println("Your hand:");
        printHand();
        System.out.print("Choose a card to play: ");
    }

    private void printScores(){
        System.out.println("-------------------------------------------------------------");
        System.out.println("Opponent's score:\n");
        System.out.print(botPlayer.getScore().toString());
        System.out.println("------------------------------------------------------------");
        System.out.println("Your score:\n");
        System.out.print(humanPlayer.getScore().toString());
        System.out.println("------------------------------------------------------------");
    }

    private void printHand(){
        if (elementBlock == null){
            for (int i = 0; i < 5; i++){
                System.out.println("[" + (i+1) + "] " + humanPlayer.getHand()[i].toString());
            }
            inputLimit = 5;
            return;
        }

        Card[] hand = humanPlayer.getHand();
        ArrayList<Card> availableCards = new ArrayList<>();

        for (int i = 0; i<5; i++){
            if (hand[i].element != elementBlock){
                availableCards.add(hand[i]);
            }
        }

        if (availableCards.isEmpty()){
            humanPlayer.newHand();
            elementBlock = null;
            printHand();
            return;
        }

        for (int i = 0; i < availableCards.size(); i++){
            System.out.println("[" + (i+1) + "]" + availableCards.get(i).toString());
            inputLimit = availableCards.size();
        }
        elementBlock = null;
    }

    private int input(){
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        int input = -1;

        while(flag){
            flag = false;
            try {
                input = Integer.parseInt(scanner.next());
                if ((input < 1) || (input > inputLimit)){
                    throw new InputMismatchException();
                }
            } catch (Exception e) {
                System.out.print("Invalid input, try again: ");
                    flag = true;
            } finally {
                scanner.close();
            }
        }
        
        return input;
    }
    
    private Queue<Card> readCSV(String file) throws IOException{
        Queue<Card> returnValue = new LinkedList<>();
        Path path = Paths.get("src/input/" + file);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())){
            String line = reader.readLine();
            Scanner scanner;

            while ((line = reader.readLine()) != null){
                scanner = new Scanner(line).useDelimiter(";");

                ELEMENT cardElement = getElement(scanner.next());
                int cardNumber = Integer.parseInt(scanner.next());
                COLOR cardColor = getColor(scanner.next());
                EFFECTTYPE cardEffect = getEffect(scanner.next());

                returnValue.add(new Card(cardElement, cardNumber, cardColor, cardEffect));
            }
        }
        catch (IOException e){
            throw new IOException("Couldn't read a file");
        }
        return returnValue;
    }

    private ELEMENT getElement(String element){
        switch(element){
            case "FIRE":
                return ELEMENT.FIRE;
            case "WATER":
                return ELEMENT.WATER;
            default:
                return ELEMENT.SNOW;
        }
    }

    private COLOR getColor(String color){
        switch(color){
            case "BLUE":
                return COLOR.BLUE;
            case "RED":
                return COLOR.RED;
            case "GREEN":
                return COLOR.GREEN;
            case "YELLOW":
                return COLOR.YELLOW;
            case "ORANGE":
                return COLOR.ORANGE;
            default:
                return COLOR.PURPLE;
        }
    }

    private EFFECTTYPE getEffect(String effect){
        switch(effect){
            case "POWER_REVERSAL":
                return EFFECTTYPE.POWER_REVERSAL;
            case "NUMBER_MODIFIER_SELF":
                return EFFECTTYPE.NUMBER_MODIFIER_SELF;
            case "NUMBER_MODIFIER_OTHER":
                return EFFECTTYPE.NUMBER_MODIFIER_OTHER;
            case "FIRE_REMOVAL":
                return EFFECTTYPE.FIRE_REMOVAL;
            case "WATER_REMOVAL":
                return EFFECTTYPE.WATER_REMOVAL;
            case "SNOW_REMOVAL":
                return EFFECTTYPE.SNOW_REMOVAL;
            case "BLUE_REMOVAL":
                return EFFECTTYPE.BLUE_REMOVAL;
            case "RED_REMOVAL":
                return EFFECTTYPE.RED_REMOVAL;
            case "GREEN_REMOVAL":
                return EFFECTTYPE.GREEN_REMOVAL;
            case "YELLOW_REMOVAL":
                return EFFECTTYPE.YELLOW_REMOVAL;
            case "ORANGE_REMOVAL":
                return EFFECTTYPE.ORANGE_REMOVAL;
            case "PURPLE_REMOVAL":
                return EFFECTTYPE.PURPLE_REMOVAL;
            case "CHANGE_FIRE_TO_SNOW":
                return EFFECTTYPE.CHANGE_FIRE_TO_SNOW;
            case "CHANGE_WATER_TO_FIRE":
                return EFFECTTYPE.CHANGE_WATER_TO_FIRE;
            case "CHANGE_SNOW_TO_WATER":
                return EFFECTTYPE.CHANGE_SNOW_TO_WATER;
            case "BLOCK_FIRE":
                return EFFECTTYPE.BLOCK_FIRE;
            case "BLOCK_WATER":
                return EFFECTTYPE.BLOCK_WATER;
            case "BLOCK_SNOW":
                return EFFECTTYPE.BLOCK_SNOW;
            default:
                return EFFECTTYPE.NO_EFFECT;
        }
    }

    private void cardBattle(int playerInput){
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
            System.out.println("YOU WIN!");
            printScores();
            return true;
        } else if (botPlayer.getScore().hasWon()){
            System.out.println("You lost!");
            printScores();
            return true;
        }
        return false;
    }
}