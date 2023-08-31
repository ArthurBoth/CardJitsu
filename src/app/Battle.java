package app;

import player.*;
import enumerations.*;
import java.util.*;

public class Battle {
    private Queue<PowerEffect> currentTurnEffects = new LinkedList<>();
    private Queue<PowerEffect> nextTurnEffects = new LinkedList<>();
    private boolean reverseBattle;
    private Card player1Card;
    private Card player2Card;

     /*
     * TODO add fire removal effect
     * TODO add water removal effect
     * TODO add snow removal effect
     * TODO add blue removal effect
     * TODO add red removal effect
     * TODO add green removal effect
     * TODO add yellow removal effect
     * TODO add orange removal effect
     * TODO add purple removal effect
     * TODO add block fire effect
     * TODO add block water effect
     * TODO add block snow effect
     */

    /*
     * Returns a positive number if player1 wins, negative if player2 wins, 0 if they draw
     */
    public int battle(Card p1Card,Card p2Card){
        int returnValue;

        this.player1Card = p1Card;
        this.player2Card = p2Card;

        getPowerEffects();
        applyInBattleEffects();
        returnValue = cardBattle();
        endBattle();

        return returnValue;
    }

    private void endBattle(){
        currentTurnEffects = nextTurnEffects;
        nextTurnEffects = new LinkedList<>();
        reverseBattle = false;
        player1Card = null;
        player2Card = null;
    }

    private int cardBattle(){
        int elementBattle = elementBattle();
        if(elementBattle != 0){
            return elementBattle;
        }

        int winner = tieBreaker();

        if (reverseBattle){
            return -winner;
        }
        return winner;
    }

    private int elementBattle(){
         return player1Card.element.elementBattle(player2Card.element);
    }

    private int tieBreaker(){
        return player1Card.compareTo(player2Card);
    }

    private void getPowerEffects(){
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

    private void applyInBattleEffects(){
        while (!currentTurnEffects.isEmpty()){
            PowerEffect power = currentTurnEffects.poll();
            switch (power.effect){
                case POWER_REVERSAL:
                    reverseBattle = true;
                    break;
                case NUMBER_MODIFIER_SELF:
                    applyNumberModifier(power);
                    break;
                case NUMBER_MODIFIER_OTHER:
                    applyNumberModifier(power);
                    break;
                case CHANGE_FIRE_TO_SNOW:
                    applyElementChange(ELEMENT.FIRE);
                    break;
                case CHANGE_WATER_TO_FIRE:
                    applyElementChange(ELEMENT.WATER);
                    break;
                case CHANGE_SNOW_TO_WATER:
                    applyElementChange(ELEMENT.SNOW);
                    break;
                default:
                    break;
            }
        }
    }

    private void applyNumberModifier(PowerEffect effect){
        switch (effect.effect){
            case NUMBER_MODIFIER_SELF:
                if (effect.getPlayerID() == 0){
                    player1Card = new Card(
                    player1Card.element, player1Card.number+2, player1Card.color, player1Card.powerEffect
                    );
                } else {
                    player2Card = new Card(
                    player2Card.element, player2Card.number+2, player2Card.color, player2Card.powerEffect
                    );
                }
                break;
                case NUMBER_MODIFIER_OTHER:
                    if (effect.getPlayerID() == 0){
                        player2Card = new Card(
                            player2Card.element, player2Card.number-2, player2Card.color, player2Card.powerEffect
                        );
                    } else {
                        player1Card = new Card(
                            player1Card.element, player1Card.number-2, player1Card.color, player1Card.powerEffect
                        );
                    }
                break;
            default:
        }
    }

    private void applyElementChange(ELEMENT elementToChange){
        switch (elementToChange){
            case FIRE:
                if (player1Card.element == ELEMENT.FIRE){
                    player1Card = new Card(
                        ELEMENT.SNOW, player1Card.number, player1Card.color, player1Card.powerEffect
                        );
                    }
                if (player2Card.element == ELEMENT.FIRE){
                    player2Card = new Card(
                        ELEMENT.SNOW, player2Card.number, player2Card.color, player2Card.powerEffect
                        );
                }
                break;
            case WATER:
                if (player1Card.element == ELEMENT.WATER){
                    player1Card = new Card(
                        ELEMENT.FIRE, player1Card.number, player1Card.color, player1Card.powerEffect
                        );
                }
                if (player2Card.element == ELEMENT.WATER){
                    player2Card = new Card(
                        ELEMENT.FIRE, player2Card.number, player2Card.color, player2Card.powerEffect
                        );
                }
                break;
            case SNOW:
                if (player1Card.element == ELEMENT.SNOW){
                    player1Card = new Card(
                        ELEMENT.WATER, player1Card.number, player1Card.color, player1Card.powerEffect
                        );
                }
                if (player2Card.element == ELEMENT.SNOW){
                    player2Card = new Card(
                        ELEMENT.WATER, player2Card.number, player2Card.color, player2Card.powerEffect
                        );
                }
                break;
            default:
        }
    }
}
