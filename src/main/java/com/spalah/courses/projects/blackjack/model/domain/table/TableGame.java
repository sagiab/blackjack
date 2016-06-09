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
    private long stepId;

    @Column(name = "bet_id")
    private long betId;

    @Column(name = "cards")
    private String cards;

    @Column(name = "player_type")
    private PlayerType playerType;

    public long getStepId() {
        return stepId;
    }

    public void setStepId(long stepId) {
        this.stepId = stepId;
    }

    public long getBetId() {
        return betId;
    }

    public void setBetId(long betId) {
        this.betId = betId;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
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
