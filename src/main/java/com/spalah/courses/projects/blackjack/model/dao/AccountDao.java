package com.spalah.courses.projects.blackjack.model.dao;

import com.spalah.courses.projects.blackjack.exception.DaoException;
import com.spalah.courses.projects.blackjack.model.domain.Account;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public interface AccountDao {

    void createAccount(Account account);

    Account getAccount(String login);

    void deleteAccount(String login);

    List<Account> getAll();
}
