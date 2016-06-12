package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.model.dao.AccountDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class AccountService {
    private static final Long STARTED_BALANCE = 10000L;
    @Autowired
    private AccountDao accountDao;

    public AccountService() {
    }

    public Account getAccount(String login) throws AccountException {
        Account account;
        try {
            account = accountDao.getAccount(login);
        } catch (NoResultException e) {
            throw new AccountException("Login incorrect");
        }
        return account;
    }

    public Account getAccount(String login, String password) throws AccountException {
        Account account;
        try {
            account = accountDao.getAccount(login);
        } catch (NoResultException e) {
            throw new AccountException("Login incorrect");
        }
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

    public void deleteAccount(String login, String password) throws AccountException {
        Account account;
        if (login != null && password != null) {
            account = getAccount(login, password);
            accountDao.deleteAccount(account.getLogin());
        } else {
            throw new AccountException("The account information is not complete");
        }
    }

    /*
    @return >0 if balance was succesfully updated or -1 if not enough balance on account
     */
    public double updateAccountBalance(Account account, double updateSum) throws AccountException {
        Double updatedBalance;
        if ((updateSum < 0 && account.getBalance() + updateSum >= 0) || updateSum >= 0) {
            updatedBalance = account.getBalance() + updateSum;
            accountDao.setBalance(account.getLogin(), updatedBalance);
            return updatedBalance;
        } else {
            throw new AccountException("Bet is bigger than your balance");
        }
    }

    private boolean isUnique(Account account) throws AccountException {
        List<Account> accounts = accountDao.getAll();
        for (Account a : accounts) {
            if (a.getLogin().equals(account.getLogin())) throw new AccountException("This login is already busy.");
        }
        return true;
    }
}
