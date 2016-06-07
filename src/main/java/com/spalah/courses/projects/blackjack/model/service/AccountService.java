package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.model.dao.AccountDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class AccountService {
    private static final Long STARTED_BALANCE = 10000L;
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account getAccount(String login, String password) throws AccountException {
        Account account = accountDao.getAccount(login);
        if (BCrypt.checkpw(password, account.getPassword())) return account;
        else throw new AccountException("Password incorrect");
    }

    public void createAccount(Account account) throws AccountException {
        if (isUnique(account)) {
            String passHash = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
            account.setPassword(passHash);
            account.setBalance(STARTED_BALANCE);
            accountDao.createAccount(account);
        }
    }

    public void deleteAccount(Account account) {
        accountDao.deleteAccount(account.getLogin());
    }

    private boolean isUnique(Account account) throws AccountException {
        List<Account> accounts = accountDao.getAll();
        for (Account a : accounts) {
            if (a.getLogin().equals(account.getLogin())) throw new AccountException("This login is already busy.");
        }
        return true;
    }
}
