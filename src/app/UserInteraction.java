package app;

import java.util.*;
import player.*;
import enumerations.*;

public class UserInteraction {

    private UserInteraction(){
        throw new IllegalStateException("Utility class");
    }

    private static int playerInputMaxLimit = 5; // inclusive

    public static void printMenu(Player player, Player bot, ELEMENT blockedElement){
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

    public static int playerInput(){
        Scanner scanner = new Scanner(System.in);
        boolean flag;
        int input = 1;

        do{
            flag = false;
            try {
                input = Integer.parseInt(scanner.next());
                if ((input < 1) || (input > playerInputMaxLimit)){
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                System.out.print("Your input is not a number, please try again: ");
                    flag = true;
            } catch (IllegalArgumentException e){
                System.out.printf("Your input must be between 1 and %d, please try again: ", playerInputMaxLimit);
            } finally {
                scanner.close();
            }
        }while(flag);
        
        return input;
    }

    public static void printCardBattle(Card playerCard, Card botCard){
        System.out.println("\n");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("You played " + playerCard.toString().toLowerCase());
        System.out.println("Your opponent played " + botCard.toString().toLowerCase());
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("\n");
    }

    public static void printBattleResults(int result){
        switch(result){
            case 1:
                System.out.println("You won this round!");
                return;
            case -1:
                System.out.println("You lost this round!");
                return;
            default:
                System.out.println("Draw!");
        }
    }

    public static void printVictoryMessage(Player player, Player bot){
        printScores(player, bot);
        System.out.println("\nYOU WON THE GAME!");
    }
    public static void printLossMessage(Player player, Player bot){
        printScores(player, bot);
        System.out.println("\nYOU LOST THE GAME!");
    }

    private static void printScores(Player player, Player bot){
        System.out.println("-------------------------------------------------------------");
        System.out.println("Opponent's score:\n");
        System.out.print(bot.getScore().toString());
        System.out.println("------------------------------------------------------------");
        System.out.println("Your score:\n");
        System.out.print(player.getScore().toString());
        System.out.println("------------------------------------------------------------");
    }
    
    private static void printHand(Player player){
        playerInputMaxLimit = 5;

        for (int i = 0; i < playerInputMaxLimit; i++){
            System.out.println("[" + (i+1) + "] " + player.getHand()[i].toString());
        }
    }
    private static void printHand(Player player, ELEMENT blockedElement){
        Card[] hand = player.getHand();
        ArrayList<Card> playableCards = new ArrayList<>();

        for (int i = 0; i<5; i++){
            if (hand[i].element != blockedElement){
                playableCards.add(hand[i]);
            }
        }

        if (playableCards.isEmpty()){
            player.newHand();
            printHand(player);
            return;
        }

        for (int i = 0; i < playableCards.size(); i++){
            System.out.println("[" + (i+1) + "]" + playableCards.get(i).toString());
            playerInputMaxLimit = playableCards.size();
        }
    }
}