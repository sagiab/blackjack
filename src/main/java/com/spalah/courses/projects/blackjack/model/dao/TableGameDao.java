package com.spalah.courses.projects.blackjack.model.dao;

import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.table.Holder;

import java.util.List;
/**
 * Created by Dima on 06.06.2016.
 */
public interface TableGameDao {

    List getPlayerCards(String playerLogin);

    void addCard(Card card, long tableId, Holder holder);
}
