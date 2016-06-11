package com.spalah.courses.projects.blackjack.model.domain.table;

import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;

import javax.persistence.*;

/**
 * Created by Dima on 08.06.2016.
 */

@Entity
@javax.persistence.Table(name = "Table_Game")
public class TableGame {
    @Id
    @Column(name = "step_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stepId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bet_id")
    private Bet bet;

    @Column(name = "cards")
    private String cards;

    @Column(name = "player_type")
    private String cardsHolder;

    public long getStepId() {
        return stepId;
    }

    public void setStepId(long stepId) {
        this.stepId = stepId;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public String getCardsHolder() {
        return cardsHolder;
    }

    public void setCardsHolder(String cardsHolder) {
        this.cardsHolder = cardsHolder;
    }

    @Override
    public String toString() {
        return "TableGame{" +
                "stepId=" + stepId +
                ", bet=" + bet +
                ", cards='" + cards + '\'' +
                ", cardsHolder='" + cardsHolder + '\'' +
                '}';
    }
}
