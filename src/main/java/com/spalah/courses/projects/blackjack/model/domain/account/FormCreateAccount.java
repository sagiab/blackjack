package com.spalah.courses.projects.blackjack.model.domain.account;

import org.mindrot.jbcrypt.BCrypt;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Denis Loshkarev on 07.06.2016.
 */
public class FormCreateAccount {
    @Size(min = 4)
    @Pattern(regexp = "^\\w+$")
    private String login;
    @Size(min = 4)
    @Pattern(regexp = "^\\w+$")
    private String nickName;
    @Size(min = 4)
    @Pattern(regexp = "^\\w+$")
    private String password;

    public FormCreateAccount() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hash(password);
    }

    private String hash(String password) {
        return password;
    }
}
