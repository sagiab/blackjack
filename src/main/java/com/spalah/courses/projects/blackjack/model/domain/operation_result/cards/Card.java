package com.spalah.courses.projects.blackjack.model.domain.operation_result.cards;

import com.spalah.courses.projects.blackjack.model.domain.operation_result.Resultable;

import java.util.Objects;

/**
 * @author Denis Loshkarev on 03.06.2016.
 * @author Dima Zasuha on 05.06.2016
 */
public class Card implements Resultable {
    private CardColor cardColor;
    private CardType cardType;
    private Holder whose; // show who own this card: player, dialer or none

    public Card(CardColor cardColor, CardType cardType, Holder whose){
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.whose = whose;
    }

    public Card(CardColor cardColor, CardType cardType) {
        this.cardColor = cardColor;
        this.cardType = cardType;
        whose = Holder.COMMON;
    }

    public Holder getWhose() {
        return whose;
    }


    public void setWhose(Holder whose) {
        this.whose = whose;
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
        return getCardType() + "." + getCardColor();
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public CardType getCardType() {
        return cardType;
    }

    @Override
    public String printResult() {
        return  getWhose() + " got card: " + toString();
    }
}
