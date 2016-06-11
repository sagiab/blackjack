package com.spalah.courses.projects.blackjack.model.dao;

import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import com.spalah.courses.projects.blackjack.model.domain.table.TableBetRange;
import com.spalah.courses.projects.blackjack.model.domain.table.TableGame;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public interface TableDao {
    Table createTable(TableType tableType, Account account);

    Table getTable(long tableId);

    /*
    *  Method returns already usedCards, so it is useful to
    *  know what cards are already out of the game
    *  @param tableId id of the current game's table
    *  @return list of used cards
    */
    List<TableGame> getSteps(long tableId);
}
