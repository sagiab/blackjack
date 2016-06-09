package com.spalah.courses.projects.blackjack.exception;

/**
 * @author Denis Loshkarev on 05.06.2016.
 */
public class AccountException extends BlackJackException {
    private Object o;

    public AccountException() {
    }

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Object o) {
        super(message);
        this.o = o;
    }

    public Object getObject() {
        return o;
    }
}
