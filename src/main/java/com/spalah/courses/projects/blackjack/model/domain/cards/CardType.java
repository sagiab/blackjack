package com.spalah.courses.projects.blackjack.model.domain.cards;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public enum CardType {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);
    private int value;

    CardType(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }

    public static void main(String[] args) {
        System.out.println(CardType.TWO.getValue());
    }
}
