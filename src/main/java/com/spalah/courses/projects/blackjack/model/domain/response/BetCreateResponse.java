package com.spalah.courses.projects.blackjack.model.domain.response;

import com.spalah.courses.projects.blackjack.model.domain.status.StatusMessage;

/**
 * @author Denis Loshkarev on 13.06.2016.
 */
public class BetCreateResponse {
    private StatusMessage status;
    private TableGameStep step;

    public BetCreateResponse() {
    }

    public BetCreateResponse(StatusMessage status, TableGameStep step) {
        this.status = status;
        this.step = step;
    }

    public StatusMessage getStatus() {
        return status;
    }

    public void setStatus(StatusMessage status) {
        this.status = status;
    }

    public TableGameStep getStep() {
        return step;
    }

    public void setStep(TableGameStep step) {
        this.step = step;
    }
}
