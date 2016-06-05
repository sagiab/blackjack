package com.spalah.courses.projects.blackjack.model.dao;

import com.spalah.courses.projects.blackjack.model.domain.Account;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public interface AccountDao {
    void createAccount(Account account);

    Account getAccount(String login);

    void deleteAccount(Account account);
}
