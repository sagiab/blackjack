package com.spalah.courses.projects.blackjack.model.dao.impl;

import com.spalah.courses.projects.blackjack.model.dao.TableTypeDao;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Dima on 09.06.2016.
 */
public class TableTypeDaoImpl implements TableTypeDao {
    private static final String GET_ALL_TABLE_TYPES = "FROM TableType";
    private static final String GET_TABLE_TYPE_BY_ID = "FROM TableType WHERE id = :id";
    private EntityManagerFactory entityManagerFactory;

    public TableTypeDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<TableType> getTableTypesVariants() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager
                .createQuery(GET_ALL_TABLE_TYPES, TableType.class)
                .getResultList();
    }

    @Override
    public TableType getTableTypeById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager
                .createQuery(GET_TABLE_TYPE_BY_ID, TableType.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
