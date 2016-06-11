package com.spalah.courses.projects.blackjack.model.dao.impl;

import com.spalah.courses.projects.blackjack.model.dao.TableDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardColor;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardType;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;
import com.spalah.courses.projects.blackjack.model.domain.table.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima on 09.06.2016.
 */
public class TableDaoImpl implements TableDao {
    private EntityManagerFactory entityManagerFactory;

    public TableDaoImpl(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public Table createTable(TableType tableType, Account account) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Table table = new Table();
        table.setType(tableType);
        table.setPlayer(account);

        entityManager.persist(table);
        entityManager.getTransaction().commit();

        return table;
    }

    @Override
    public TableBetRange getTableBetRange(long tableId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Table table = entityManager.createQuery("FROM Table where tableId = :id", Table.class)
                .setParameter("id", tableId)
                .getSingleResult();
        TableType tableType = table.getType();
        return new TableBetRange(tableType.getMinBetSize(), tableType.getMaxBetSize());
    }

    /*
     *  Method returns already usedCards, so it is useful to know what cards are already out of the game
     *  @param tableId id of the current game's table
     *  @return list of used cards
     */
    @Override
    public List<TableGame> getSteps(long tableId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //get table with id
        Table table = entityManager.createQuery("FROM Table where tableId = :id", Table.class)
                .setParameter("id", tableId)
                .getSingleResult();
        Bet bet = table.getBets().get(0); // только один Bet на table
        //get all steps for this bet

        return entityManager.createQuery("FROM TableGame where bet.betId = :betId", TableGame.class)
                .setParameter("betId", bet.getBetId())
                .getResultList();
    }



    public static void main(String[] args) {
        String PERSISTENCE_UNIT = "com.spalah.courses.projects.blackjack";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        TableDao tableDao = new TableDaoImpl(entityManagerFactory);
        testGetUsedCards(tableDao);
    }

    public static void testTableBetRange(TableDao tableDao){
        System.out.println(tableDao.getTableBetRange(1));
    }

    public static void testCreateTable(TableDao tableDao){
        TableType tableType = new TableType();
        tableType.setId(1);
        Account account = new Account();
        account.setId(2L);
        System.out.println(tableDao.createTable(tableType, account));
    }

    public static void testGetUsedCards(TableDao tableDao){
        tableDao.getSteps(1);
    }
}
