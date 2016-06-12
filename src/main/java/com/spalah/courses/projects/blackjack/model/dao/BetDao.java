package com.spalah.courses.projects.blackjack.model.dao;

import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;

/**
 * Created by Dima on 12.06.2016.
 */
public interface BetDao {
    void addBet(Long tableId, int betSize);

    Bet getBet(long tableId);

    Bet deleteBet(long tableId);
}
