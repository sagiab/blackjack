package com.spalah.courses.projects.blackjack.model.domain.commands;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class CommandBet extends Command {
    private Long sum;

    public CommandBet() {
        super();
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }
}
