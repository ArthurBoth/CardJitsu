package app;

import player.*;
import enumerations.*;
import java.util.*;

public class Battle {
    private Queue<PowerEffect> postBattleEffects = new LinkedList<>();
    private Queue<PowerEffect> currentTurnEffects = new LinkedList<>();
    private Queue<PowerEffect> nextTurnEffects = new LinkedList<>();
    private boolean reverseBattle;
    private Card player1Card;
    private Card player2Card;
    private int battleWinnerID;

    /*
     * Returns a positive number if player1 wins, negative if player2 wins, 0 if they draw
     */
    public int battle(Card p1Card,Card p2Card){
        int battleResult;
        this.player1Card = p1Card;
        this.player2Card = p2Card;

        getPowerEffects();
        applyInBattleEffects();
        battleResult = cardBattle();
        endBattle(battleResult);

        return battleResult;
    }

    public Queue<PowerEffect> getPostBattleEffects(){
        Queue<PowerEffect> returnValue = new LinkedList<>();
        PowerEffect power;

        while (!postBattleEffects.isEmpty()){
            power = postBattleEffects.poll();

            if (power.getPlayerID() == battleWinnerID)
                returnValue.add(power);
        }
        return returnValue;
    }

    private void endBattle(int battleResult){
        if (battleResult > 0){
            battleWinnerID = 1;
        } else if (battleResult < 0){
            battleWinnerID = 2;
        }
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
        if (player1Card.powerEffect != EFFECTTYPE.NO_EFFECT){
            if (PowerEffect.applyNextTurn(player1Card.powerEffect)){
                nextTurnEffects.add(new PowerEffect(player1Card.powerEffect, 1));
            } else {
                postBattleEffects.add(new PowerEffect(player1Card.powerEffect, 1));
            }
        }
        
        if (player2Card.powerEffect != EFFECTTYPE.NO_EFFECT){
            if (PowerEffect.applyNextTurn(player2Card.powerEffect)){
                nextTurnEffects.add(new PowerEffect(player2Card.powerEffect, 2));
            } else {
                postBattleEffects.add(new PowerEffect(player2Card.powerEffect, 2));
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
                    applyModifier(power,2);
                    break;
                case NUMBER_MODIFIER_OTHER:
                    applyModifier(power,-2);
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
                    postBattleEffects.add(power);
            }
        }
    }

    private void applyModifier(PowerEffect effect, int numberModifier){
        if (numberModifier > 0){
            if (effect.getPlayerID() == 1){
                player1Card = new Card(
                player1Card.element, player1Card.number + numberModifier, player1Card.color, player1Card.powerEffect
                );
            } else {
                player2Card = new Card(
                player2Card.element, player2Card.number + numberModifier, player2Card.color, player2Card.powerEffect
                );
            }
        } else {
            if (effect.getPlayerID() == 1){
                player2Card = new Card(
                player2Card.element, player2Card.number + numberModifier, player2Card.color, player2Card.powerEffect
                );
            } else {
                player1Card = new Card(
                player1Card.element, player1Card.number + numberModifier, player1Card.color, player1Card.powerEffect
                );
            }
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
