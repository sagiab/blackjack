package com.spalah.courses.projects.blackjack.model.dao.impl;

import com.spalah.courses.projects.blackjack.model.dao.BetDao;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Dima on 12.06.2016.
 */
public class BetDaoImpl implements BetDao {
    private static final String SQL_GET_BET_BY_TABLE_ID = "FROM Bet where table_id = :tableId";
    private EntityManagerFactory entityManagerFactory;

    public BetDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void addBet(Long tableId, int betSize) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Bet bet = new Bet();
        bet.setTableId(tableId);
        bet.setBetSize(betSize);

        entityManager.persist(bet);
        entityManager.getTransaction().commit();
    }

    @Override
    public Bet getBet(long tableId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Bet bet = entityManager.createQuery(SQL_GET_BET_BY_TABLE_ID, Bet.class)
                .setParameter("tableId", tableId).getSingleResult();
        return bet;
    }

    @Override
    public Bet deleteBet(long tableId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Bet bet = entityManager.createQuery(SQL_GET_BET_BY_TABLE_ID, Bet.class)
                .setParameter("tableId", tableId).getSingleResult();
        entityManager.remove(bet);
        entityManager.getTransaction().commit();
        return bet;
    }
}
