package app;
import player.*;
import java.util.*;

public class GameSystem {
    
    private Player humanPlayer;
    private PlayerBot botPlayer;
    private Battle judge;

    public GameSystem(){
        this.humanPlayer = new Player();
        this.botPlayer = new PlayerBot();
        this.judge = new Battle();
    }

    public void play(){
        setup();
        while(finishGame()){
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
        Card playerCard = humanPlayer.playCard(playerInput-1);
        Card botCard = botPlayer.playCard();
        
        System.out.println("\n");
        System.out.println("-------------------------------------------------------------------------------");

        System.out.println("You played " + playerCard.toString().toLowerCase());
        System.out.println("Your opponent played " + botCard.toString().toLowerCase());

        int battleResult = judge.battle(playerCard, botCard);

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

    private boolean finishGame(){
        if (humanPlayer.getScore().hasWon()){
            System.out.println("You won!");
            return false;
        } else if (botPlayer.getScore().hasWon()){
            System.out.println("You lost!");
            return false;
        }
        return true;
    }
}