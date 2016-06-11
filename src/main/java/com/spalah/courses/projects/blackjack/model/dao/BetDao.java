package com.spalah.courses.projects.blackjack.model.dao;

import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;

/**
 * Created by Dima on 12.06.2016.
 */
public interface BetDao {
    Bet addBet(Table table, int betSize);
}
