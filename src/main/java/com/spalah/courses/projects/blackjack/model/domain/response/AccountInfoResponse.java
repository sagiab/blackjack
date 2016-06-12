package com.spalah.courses.projects.blackjack.model.domain.response;

import com.spalah.courses.projects.blackjack.model.domain.account.Account;

/**
 * @author Denis Loshkarev on 10.06.2016.
 */
public class AccountInfoResponse {
    private String nickName;
    private Double balance;

    public AccountInfoResponse(Account account) {
        this.nickName = account.getNickName();
        this.balance = account.getBalance();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
