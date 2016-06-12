package com.spalah.courses.projects.blackjack.model.dao;

import com.spalah.courses.projects.blackjack.model.domain.table.TableGame;

import java.util.List;

/**
 * Created by Dima on 06.06.2016.
 */
public interface TableGameDao {
    List getPlayerCards(String playerLogin);

    void addCard(TableGame tableGame);
}
