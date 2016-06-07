package com.spalah.courses.projects.blackjack.exception;

/**
 * @author Dima Zasuha on 05.06.2016
 * @author Denis Loshkarev on 05.06.2016.
 */
public class AllCardsWereUsedException extends BlackJackException {
    public AllCardsWereUsedException() {
        super("All cards were already used");
    }
}