package com.spalah.courses.projects.blackjack.model.dao.impl;

import com.spalah.courses.projects.blackjack.model.dao.BetDao;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Dima on 12.06.2016.
 */
public class BetDaoImpl implements BetDao {
    private EntityManagerFactory entityManagerFactory;

    public BetDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Bet addBet(Table table, int betSize) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Bet bet = new Bet();
        bet.setTable(table);
        bet.setBetSize(betSize);

        entityManager.persist(bet);
        entityManager.getTransaction().commit();

        return bet;
    }

    @Override
    public Bet getBet(long tableId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Bet bet = entityManager.createQuery("FROM Bet where tableId =:tableId", Bet.class)
                .setParameter("tableId", tableId).getSingleResult();

        entityManager.getTransaction().commit();

        return bet;
    }
}
