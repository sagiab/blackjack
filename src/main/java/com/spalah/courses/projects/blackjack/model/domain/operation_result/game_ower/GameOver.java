package com.spalah.courses.projects.blackjack.model.domain.operation_result.game_ower;

import com.spalah.courses.projects.blackjack.model.domain.operation_result.Resultable;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.Card;

import java.util.List;

/**
 * Created by Dima on 12.06.2016.
 */
public class GameOver implements Resultable {
    private GameOutcome gameOutcome;

    private List<Card> dealerCards;
    private List<Card> playerCards;

    private int playerSum;
    private int dealerSum;

    public GameOver(GameOutcome gameOutcome, List<Card> dealerCards, int dealerSum, List<Card> playerCards, int playerSum) {
        this.gameOutcome = gameOutcome;
        this.dealerCards = dealerCards;
        this.playerCards = playerCards;
        this.playerSum = playerSum;
        this.dealerSum = dealerSum;
    }

    public GameOutcome getGameOutcome() {
        return gameOutcome;
    }

    @Override
    public String toString() {
        return "Resultable{" +
                "gameOutcome=" + gameOutcome +
                ", dealerCards=" + dealerCards +
                ", playerCards=" + playerCards +
                ", playerSum=" + playerSum +
                ", dealerSum=" + dealerSum +
                '}';
    }

    @Override
    public String printResult() {
        return toString();
    }
}
