package com.spalah.courses.projects.blackjack.model.dao;

import com.spalah.courses.projects.blackjack.model.domain.account.Account;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public interface AccountDao {

    void createAccount(Account account);

    Account getAccount(String login);

    void deleteAccount(String login);

    List<Account> getAll();

    /*
     *  update player's balance when player deals by updateBalance that is less than previous balance on the size
     *  of the bet and if player wins by updateBalance  that is more than previous balance on the size of the win
     *  @param account account which is gonna be updated
     *  @param updateBalance new value of balance
     *  @return true if balance was updated or -1 if player has not enough balance
     */
    void setBalance(String login, double updateBalance);
}
