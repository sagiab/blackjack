package com.spalah.courses.projects.blackjack.model.dao.impl;

import com.spalah.courses.projects.blackjack.model.dao.TableTypeDao;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Dima on 09.06.2016.
 */
public class TableTypeDaoImpl implements TableTypeDao {
    private static final String GET_ALL_TABLE_TYPES = "FROM TableType";
    private EntityManagerFactory entityManagerFactory;

    public TableTypeDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public List<TableType> getTableTypesVariants() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery(GET_ALL_TABLE_TYPES, TableType.class).getResultList();
    }



    public static void main(String[] args) {
        String PERSISTENCE_UNIT = "com.spalah.courses.projects.blackjack";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        TableTypeDao tableTypeDao = new TableTypeDaoImpl(entityManagerFactory);
        System.out.println(tableTypeDao.getTableTypesVariants());
    }
}
