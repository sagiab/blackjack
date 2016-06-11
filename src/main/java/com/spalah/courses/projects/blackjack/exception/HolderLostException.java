package com.spalah.courses.projects.blackjack.exception;

/**
 * Created by Dima on 10.06.2016.
 */
public class HolderLostException extends BlackJackException {
    public HolderLostException(int cardsSum) {
        super("Too many cards, sum = " + cardsSum);
    }

}
