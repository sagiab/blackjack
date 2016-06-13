package com.spalah.courses.projects.blackjack.model.domain.response;

import com.spalah.courses.projects.blackjack.model.domain.cards.Card;

import java.util.List;

/**
 * @author Denis Loshkarev on 13.06.2016.
 */
public class TableGameStep {
    private List<Card> playerCards;
    private List<Card> dealerCards;
    private int playerSum;
    private int dealerSum;

    public TableGameStep() {
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

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(List<Card> dealerCards) {
        this.dealerCards = dealerCards;
    }

    public int getDealerSum() {
        return dealerSum;
    }

    public void setDealerSum(int dealerSum) {
        this.dealerSum = dealerSum;
    }
}
