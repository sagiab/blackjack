package com.spalah.courses.projects.blackjack.exception;

/**
 * @author Denis Loshkarev on 05.06.2016.
 */
public class BlackJackException extends Exception {
    public BlackJackException() {
    }

    public BlackJackException(String message) {
        super(message);
    }

    public BlackJackException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlackJackException(Throwable cause) {
        super(cause);
    }

    public BlackJackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
