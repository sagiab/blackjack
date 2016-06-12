package com.spalah.courses.projects.blackjack.model.domain.account;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "player_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "nickname")
    private String nickName;
    @Column(name = "balance")
    private double balance;
    @Column(name = "password")
    private String password;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("login", login)
                .append("nickName", nickName)
                .append("balance", balance)
                .append("pass", password)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return new EqualsBuilder()
                .append(balance, account.balance)
                .append(id, account.id)
                .append(login, account.login)
                .append(nickName, account.nickName)
                .append(password, account.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(login)
                .append(nickName)
                .append(balance)
                .append(password)
                .toHashCode();
    }
}
