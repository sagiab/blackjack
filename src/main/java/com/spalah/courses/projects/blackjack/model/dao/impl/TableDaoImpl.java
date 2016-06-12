package com.spalah.courses.projects.blackjack.model.dao.impl;

import com.spalah.courses.projects.blackjack.model.dao.TableDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import com.spalah.courses.projects.blackjack.model.domain.table.TableGame;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Dima on 09.06.2016.
 */
public class TableDaoImpl implements TableDao {
    private static final String SQL_GET_TABLE_BY_TABLE_ID =
            "FROM Table t JOIN FETCH t.type tt JOIN FETCH t.player tp " +
                    "where t.tableId = :id";
    private static final String SQL_GET_STEPS_BY_TABLE_ID =
            "FROM TableGame tg JOIN FETCH tg.bet tgb " +
                    "where tgb.tableId = :tableId";
    private EntityManagerFactory entityManagerFactory;

    public TableDaoImpl(EntityManagerFactory entityManagerFactory) {
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
    public Table getTable(long tableId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery(SQL_GET_TABLE_BY_TABLE_ID, Table.class)
                .setParameter("id", tableId)
                .getSingleResult();
    }

    /*
     *  Method returns already usedCards, so it is useful to know what cards are already out of the game
     *  @param tableId id of the current game's table
     *  @return list of used cards
     */
    @Override
    public List<TableGame> getSteps(long tableId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery(SQL_GET_STEPS_BY_TABLE_ID, TableGame.class)
                .setParameter("tableId", tableId)
                .getResultList();
    }
}
