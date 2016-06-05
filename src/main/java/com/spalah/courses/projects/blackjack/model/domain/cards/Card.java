package com.spalah.courses.projects.blackjack.model.domain.cards;

import java.util.Objects;

/**
 * @author Denis Loshkarev on 03.06.2016.
 * @author Dima Zasuha on 05.06.2016
 */
public class Card {
    private CardColor cardColor;
    private CardType cardType;

    public Card(CardColor cardColor, CardType cardType){
        this.cardColor = cardColor;
        this.cardType = cardType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardColor,cardType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Card thatCard = (Card) obj;
        return this.getCardColor().equals(thatCard.getCardColor()) &&
                this.getCardType().equals(thatCard.getCardType());
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardColor=" + cardColor +
                ", cardType=" + cardType +
                '}';
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public CardType getCardType() {
        return cardType;
    }
}
