package com.spalah.courses.projects.blackjack.model.dao.impl;

import com.spalah.courses.projects.blackjack.model.dao.TableGameDao;
import com.spalah.courses.projects.blackjack.model.domain.table.TableGame;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Dima on 10.06.2016.
 */
public class TableGameDaoImpl implements TableGameDao {
    private EntityManagerFactory entityManagerFactory;

    public TableGameDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List getPlayerCards(String playerLogin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addCard(TableGame tableGame) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(tableGame);
        entityManager.getTransaction().commit();
    }
}
