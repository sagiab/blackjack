package com.spalah.courses.projects.blackjack.model.domain.account;

/**
 * @author Denis Loshkarev on 08.06.2016.
 */
public class FormLoginAccount {
    private String login;
    private String password;

    public FormLoginAccount() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
