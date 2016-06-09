package com.spalah.courses.projects.blackjack.model.domain.table;

/**
 * Created by Dima on 09.06.2016.
 */
public class TableBetRange {
    private int maxBet;
    private int minBet;

    public TableBetRange(int maxBet, int minBet) {
        this.maxBet = maxBet;
        this.minBet = minBet;
    }

    public int getMaxBet() {
        return maxBet;
    }

    public int getMinBet() {
        return minBet;
    }

    @Override
    public String toString() {
        return "TableBetRange{" +
                "maxBet=" + maxBet +
                ", minBet=" + minBet +
                '}';
    }
}
