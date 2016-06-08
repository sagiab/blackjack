package com.spalah.courses.projects.blackjack.model.domain;

/**
 * Created by Denis Loshkarev.
 */
public class StatusMessage {
    private int status;
    private String message;

    public StatusMessage() {
    }

    public StatusMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public StatusMessage(Throwable e) {
        this.status = -1;
        this.message = e.getMessage();
    }

    public StatusMessage well() {
        this.status = 0;
        this.message = "All is well";
        return this;
    }
}
