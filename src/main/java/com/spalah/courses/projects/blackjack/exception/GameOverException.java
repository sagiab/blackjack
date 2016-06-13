package com.spalah.courses.projects.blackjack.exception;

/**
 * @author Denis Loshkarev on 13.06.2016.
 */
public class GameOverException extends BlackJackException {
    private String login;
    private long tableId;

    public GameOverException(String login, long tableId) {
        this.login = login;
        this.tableId = tableId;
    }

    public long getTableId() {
        return tableId;
    }

    public String getLogin() {
        return login;
    }
}
