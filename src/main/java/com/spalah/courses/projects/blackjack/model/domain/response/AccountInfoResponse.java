package com.spalah.courses.projects.blackjack.model.domain.response;

import com.spalah.courses.projects.blackjack.model.domain.account.Account;

/**
 * @author Denis Loshkarev on 10.06.2016.
 */
public class AccountInfoResponse {
    private String nickName;
    private Long balance;

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

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
