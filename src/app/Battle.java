package app;

import player.Card;

public class Battle {
    public Battle(){}

    /*
     * Returns a positive number if player1 wins, negative if player2 wins, 0 if draw
     */
    public int battle(Card player1Card,Card player2Card){
        int elementBattle = elementBattle(player1Card,player2Card);
        if(elementBattle != 0){
            return elementBattle;
        }
        return tieBreaker(player1Card, player2Card);
    }

    private int elementBattle(Card player1Card,Card player2Card){
         return player1Card.element.elementBattle(player2Card.element);
    }

    private int tieBreaker(Card player1Card,Card player2Card){
        return player1Card.compareTo(player2Card);
    }
}
