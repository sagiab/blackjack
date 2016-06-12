package com.spalah.courses.projects.blackjack.model.domain.status;

/**
 * Created by Denis Loshkarev.
 */
public class StatusMessage {
    private StatusType status;
    private String message;

    public StatusMessage() {
    }

    public StatusMessage error(Throwable e) {
        this.status = StatusType.ERROR;
        this.message = e.getMessage();
        return this;
    }

    public StatusMessage well(String message) {
        this.status = StatusType.SUCCESS;
        this.message = message;
        return this;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
