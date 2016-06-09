package com.spalah.courses.projects.blackjack.model.domain.bet;

import javax.persistence.*;

/**
 * Created by Dima on 08.06.2016.
 */
@Entity
@javax.persistence.Table(name = "Bet-Player-bet")
public class Bet {
    @Id
    @Column(name = "bet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long betId;

    @Column(name = "table_id")
    private long tableId;

    @Column(name = "bet_size")
    private int betSize;

    public long getBetId() {
        return betId;
    }

    public void setBetId(long betId) {
        this.betId = betId;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public int getBetSize() {
        return betSize;
    }

    public void setBetSize(int betSize) {
        this.betSize = betSize;
    }
}