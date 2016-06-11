package com.spalah.courses.projects.blackjack.model.domain.commands;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class Command {
    private CommandType type;
    private Boolean isAvailable;

    public Command() {
    }

    public Command(CommandType type) {
        this.type = type;
    }

    public Command available() {
        this.isAvailable = true;
        return this;
    }

    public Command banned() {
        this.isAvailable = false;
        return this;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
