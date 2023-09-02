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
        System.out.println("Opponent's score:");
        System.out.println(botPlayer.getScore().toString());
        System.out.println("------------------------------------------------------------");
        System.out.println("Your score:");
        System.out.println(humanPlayer.getScore().toString());
        System.out.print("\n");
        System.out.println("Your hand:");
        printPlayerHand();
        System.out.print("Choose a card to play: ");
    }

    private void printPlayerHand(){
        /* 
        * TODO check if any color is blocked
        * if it is, check if player has any card of that element
        * check if they ONLY have cards of that element
        * if they do, redraw hand, shuffle deck and disable the element block
        * if they don't, print only cards that are not of that element
        * prevent player from choosing cards of that element
        */

        // for now, just print the whole hand
        System.out.println("[1] " + humanPlayer.getHand()[0].toString());
        System.out.println("[2] " + humanPlayer.getHand()[1].toString());
        System.out.println("[3] " + humanPlayer.getHand()[2].toString());
        System.out.println("[4] " + humanPlayer.getHand()[3].toString());
        System.out.println("[5] " + humanPlayer.getHand()[4].toString());
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
            System.out.println("You won!");
            return true;
        } else if (botPlayer.getScore().hasWon()){
            System.out.println("You lost!");
            return true;
        }
        return false;
    }
}