package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.model.dao.TableGameDao;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;

import java.util.List;

/**
 * Created by Dima on 06.06.2016.
 */
public class TableGameService implements TableGameDao {
    @Override
    public List<Card> getPlayerCards(String playerLogin) {
        return null;
    }
}
