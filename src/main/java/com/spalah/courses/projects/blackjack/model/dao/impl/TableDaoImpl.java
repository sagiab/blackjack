package com.spalah.courses.projects.blackjack.model.dao.impl;

import com.spalah.courses.projects.blackjack.model.dao.TableDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import com.spalah.courses.projects.blackjack.model.domain.table.TableBetRange;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        return null;
    }

    @Override
    public List<Command> getAvailableCommands(Table table) {
        return null;
    }

    @Override
    public TableBetRange getTableBetRange(long tableId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Table> table =  entityManager.createQuery("FROM Table", Table.class).getResultList();
        System.out.println(table);
        return null;
    }


    public static void main(String[] args) {
        String PERSISTENCE_UNIT = "com.spalah.courses.projects.blackjack";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        TableDao tableDao = new TableDaoImpl(entityManagerFactory);
        System.out.println(tableDao.getTableBetRange(1));
    }
}
