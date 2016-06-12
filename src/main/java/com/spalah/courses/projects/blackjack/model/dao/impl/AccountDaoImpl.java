package com.spalah.courses.projects.blackjack.model.dao.impl;

import com.spalah.courses.projects.blackjack.model.dao.AccountDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author Denis Loshkarev on 05.06.2016.
 */
public class AccountDaoImpl implements AccountDao {
    private static final String GET_ALL_ACCOUNTS = "FROM Account";
    private EntityManagerFactory entityManagerFactory;

    public AccountDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void createAccount(Account account) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
    }

    @Override
    public Account getAccount(String login) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> c = cq.from(Account.class);
        cq.select(c);

        if (login != null) {
            ParameterExpression<String> p = cb.parameter(String.class, "login");
            cq.where(
                    cb.equal(c.get("login"), p)
            );
        }

        TypedQuery<Account> q = entityManager.createQuery(cq);
        if (login != null) {
            q.setParameter("login", login);
        }
        return q.getSingleResult();
    }

    @Override
    public void deleteAccount(String login) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Account WHERE login = :login")
                .setParameter("login", login)
                .executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Account> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery(GET_ALL_ACCOUNTS, Account.class).getResultList();
    }

    @Override
    public void setBalance(String login, double updateBalance) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery("Update Account SET balance =:updateBalance where login =:login")
                .setParameter("updateBalance", updateBalance).setParameter("login", login).executeUpdate();

        entityManager.getTransaction().commit();
    }
}
