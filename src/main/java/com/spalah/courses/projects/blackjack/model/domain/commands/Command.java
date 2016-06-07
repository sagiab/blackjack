package com.spalah.courses.projects.blackjack.model.domain.commands;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class Command {
    private CommandType type;

    public Command() {
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }
}
