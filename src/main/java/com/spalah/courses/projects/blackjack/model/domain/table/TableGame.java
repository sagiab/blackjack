package com.spalah.courses.projects.blackjack.model.domain.table;

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
    private int stepId;

    @Column(name = "bet_id")
    private int betId;

    @Column(name = "cards")
    private String cards;

    @Column(name = "player_type")
    private String playerType;

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getBetId() {
        return betId;
    }

    public void setBetId(int betId) {
        this.betId = betId;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    @Override
    public String toString() {
        return "TableGame{" +
                "stepId=" + stepId +
                ", betId=" + betId +
                ", cards='" + cards + '\'' +
                ", playerType='" + playerType + '\'' +
                '}';
    }
}
