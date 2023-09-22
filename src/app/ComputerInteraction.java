package app;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.Charset;
import java.util.*;

import player.*;
import enumerations.*;

public class ComputerInteraction {

    private ComputerInteraction() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static int playerInputMaxLimit = 5; // inclusive

    public static void printMenu(Player player, Player bot, ELEMENT blockedElement) {
        // TODO display card power effects
        printScores(player, bot);
        System.out.println("Your hand:\n");

        if (blockedElement == null){
            printHand(player);
        } else {
            printHand(player, blockedElement);
        }

        System.out.print("Choose a card to play: ");
    }

    public static int playerInput() {
        Scanner scanner = new Scanner(System.in);
        boolean flag;
        int input = 1;

        do{
            flag = false;
            try {
                input = Integer.parseInt(scanner.next());
                if ((input < 1) || (input > playerInputMaxLimit)) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                System.out.print("Your input is not a number, please try again: ");
                flag = true;
            } catch (IllegalArgumentException e) {
                System.out.printf("Your input must be between 1 and %d, please try again: ", playerInputMaxLimit);
                flag = true;
            } catch (Exception e) {
                System.out.println("Something went wrong, please try again: ");
                flag = true;
            }
        } while(flag);

        return input;
    }

    public static void printCardBattle(Card playerCard, Card botCard) {
        System.out.println("\n");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("You played " + playerCard.toString().toLowerCase());
        System.out.println("Your opponent played " + botCard.toString().toLowerCase());
        System.out.println("-------------------------------------------------------------------------------");
    }

    public static void printBattleResults(int result) {
        System.out.print(ANSI_YELLOW);
        switch(result){
            case 1:
                System.out.println("You won this round!");
                break;
            case -1:
                System.out.println("You lost this round!");
                break;
            default:
                System.out.println("Draw!");
        }
        System.out.print(ANSI_RESET);
    }

    public static void printVictoryMessage(Player player, Player bot) {
        printScores(player, bot);
        System.out.print(ANSI_GREEN);
        System.out.println("\nYOU WON THE GAME!");
        System.out.print(ANSI_RESET);
    }

    public static void printDefeatMessage(Player player, Player bot) {
        printScores(player, bot);
        System.out.print(ANSI_YELLOW);
        System.out.println("\nYOU LOST THE GAME!");
        System.out.print(ANSI_RESET);
    }

    public static Queue<Card> readCSV(String fileName) throws IOException, NumberFormatException {
        Queue<Card> returnValue = new LinkedList<>();
        Path path = Paths.get("src/input/" + fileName);

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = reader.readLine();
            Scanner scanner;

            while ((line = reader.readLine()) != null) {
                scanner = new Scanner(line).useDelimiter(";");

                ELEMENT cardElement = getElement(scanner.next());
                int cardNumber = Integer.parseInt(scanner.next());
                COLOR cardColor = getColor(scanner.next());
                EFFECTTYPE cardEffect = getEffect(scanner.next());

                returnValue.add(new Card(cardElement, cardNumber, cardColor, cardEffect));
            }

        } catch (IOException e) {
            throw new IOException("Something went wrong while trying to read the file.");
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Could not convert the card number to an integer.");
        }
        
        return returnValue;
    }

    private static void printScores (Player player, Player bot) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("Opponent's score:\n");
        System.out.print(bot.getScore().toString());
        System.out.println("------------------------------------------------------------");
        System.out.println("Your score:\n");
        System.out.print(player.getScore().toString());
        System.out.println("------------------------------------------------------------");
    }
    
    private static void printHand(Player player) {
        playerInputMaxLimit = 5;

        for (int i = 0; i < playerInputMaxLimit; i++){
            System.out.println("[" + (i+1) + "] " + player.getHand()[i].toString());
        }
    }

    private static void printHand(Player player, ELEMENT blockedElement) {
        Card[] hand = player.getHand();
        ArrayList<Card> playableCards = new ArrayList<>();

        for (int i = 0; i<5; i++) {
            if (hand[i].element != blockedElement) {
                playableCards.add(hand[i]);
            }
        }

        if (playableCards.isEmpty()) {
            printNoCardsAvailableMessage();
            player.newHand();
            printHand(player);
            return;
        }

        playerInputMaxLimit = playableCards.size();

        for (int i = 0; i < playerInputMaxLimit; i++) {
            System.out.println("[" + (i+1) + "]" + playableCards.get(i).toString());
        }
    }

    private static void printNoCardsAvailableMessage() {
        System.out.print(ANSI_RED);
        System.out.println("You can't play any cards due to the blocked element.");
        System.out.println("Your hand has been replaced and the effect is no longer active.");
        System.out.print(ANSI_RESET);
    }

    private static ELEMENT getElement(String element) {
        switch(element){
            case "FIRE":
                return ELEMENT.FIRE;
            case "WATER":
                return ELEMENT.WATER;
            default:
                return ELEMENT.SNOW;
        }
    }

    private static COLOR getColor(String color) {
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

    private static EFFECTTYPE getEffect(String effect) {
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
}