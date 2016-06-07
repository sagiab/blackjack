package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.DaoException;
import com.spalah.courses.projects.blackjack.model.dao.AccountDao;
import com.spalah.courses.projects.blackjack.model.domain.Account;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class AccountService {
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account getAccount(String login) {
        return accountDao.getAccount(login);
    }

    public void createAccount(Account account) throws DaoException {
        if (isValid(account)) {
            accountDao.createAccount(account);
        }
    }

    public void deleteAccount(Account account) {
        accountDao.deleteAccount(account.getLogin());
    }

    private boolean isValid(Account account) throws DaoException {
        List<Account> accounts = accountDao.getAll();
        for (Account a : accounts) {
            if (a.getLogin().equals(account.getLogin())) throw new DaoException("This login is already busy.");
        }
        return true;
    }
}
