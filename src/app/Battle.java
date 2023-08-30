package app;

import player.*;
import enumerations.*;
import java.util.*;

public class Battle {
    private Queue<PowerEffect> currentTurnEffects = new LinkedList<>();
    private Queue<PowerEffect> nextTurnEffects = new LinkedList<>();
    public Battle(){}

     /* TODO
     * add power reversal effect
     * add number modifier self effect
     * add number modifier other effect
     * add fire removal effect
     * add water removal effect
     * add snow removal effect
     * add blue removal effect
     * add red removal effect
     * add green removal effect
     * add yellow removal effect
     * add orange removal effect
     * add purple removal effect
     * add change fire to snow effect
     * add change water to fire effect
     * add change snow to water effect
     * add block fire effect
     * add block water effect
     * add block snow effect
     */

    /*
     * Returns a positive number if player1 wins, negative if player2 wins, 0 if draw
     */
    public int battle(Card player1Card,Card player2Card){
        return 0;
    }

    private int cardBattle(Card player1Card,Card player2Card){
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

    private void getPowerEffects(Card player1Card,Card player2Card){
        if (player1Card.powerEffect == EFFECTTYPE.NO_EFFECT){
            if (PowerEffect.applyThisTurn(player1Card.powerEffect)){
                currentTurnEffects.add(new PowerEffect(player1Card.powerEffect, 0));
            } else {
                nextTurnEffects.add(new PowerEffect(player1Card.powerEffect, 0));
            }
        }
        
        if (player2Card.powerEffect == EFFECTTYPE.NO_EFFECT){
            if (PowerEffect.applyThisTurn(player2Card.powerEffect)){
                currentTurnEffects.add(new PowerEffect(player2Card.powerEffect, 1));
            } else {
                nextTurnEffects.add(new PowerEffect(player2Card.powerEffect, 1));
            }
        }
    }
}
