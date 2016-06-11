package com.spalah.courses.projects.blackjack.model.dao.impl;

import com.spalah.courses.projects.blackjack.model.dao.TableGameDao;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import com.spalah.courses.projects.blackjack.model.domain.table.TableGame;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Dima on 10.06.2016.
 */
public class TableGameDaoImpl implements TableGameDao {
    private EntityManagerFactory entityManagerFactory;

    public TableGameDaoImpl(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;

    }

    @Override
    public List getPlayerCards(String playerLogin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addCard(Card card, long tableId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        TableGame tableGame = new TableGame();
        Table table = entityManager.createQuery("FROM Table where tableId =:tableId", Table.class).setParameter("tableId", tableId).getSingleResult();
        Bet tableBet = table.getBets().get(0);
        tableGame.setBet(tableBet);
        tableGame.setCards(card.toString());
        tableGame.setCardsHolder(card.getWhose().toString());

        entityManager.persist(tableGame);
        entityManager.getTransaction().commit();
    }

    public static void main(String[] args) {
        String PERSISTENCE_UNIT = "com.spalah.courses.projects.blackjack";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        TableGameDao tableGameDao = new TableGameDaoImpl(entityManagerFactory);
    }
}
