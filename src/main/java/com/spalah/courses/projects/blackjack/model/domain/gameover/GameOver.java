package com.spalah.courses.projects.blackjack.model.domain.gameover;

import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Dima on 12.06.2016.
 */
public class GameOver {
    private GameOutcome gameOutcome;

    private List<Card> dealerCards;
    private List<Card> playerCards;

    private int playerSum;
    private int dealerSum;

    private Double profit;

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

    public void setGameOutcome(GameOutcome gameOutcome) {
        this.gameOutcome = gameOutcome;
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(List<Card> dealerCards) {
        this.dealerCards = dealerCards;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public int getPlayerSum() {
        return playerSum;
    }

    public void setPlayerSum(int playerSum) {
        this.playerSum = playerSum;
    }

    public int getDealerSum() {
        return dealerSum;
    }

    public void setDealerSum(int dealerSum) {
        this.dealerSum = dealerSum;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("gameOutcome", gameOutcome)
                .append("dealerCards", dealerCards)
                .append("playerCards", playerCards)
                .append("playerSum", playerSum)
                .append("dealerSum", dealerSum)
                .append("profit", profit)
                .toString();
    }
}
