package com.spalah.courses.projects.blackjack.model.domain.communication;

import javax.persistence.*;

/**
 * Created by Dima on 08.06.2016.
 */
@Entity
@Table(name = "Bet-Player-communication")
public class BetPlayerCommunication {
    @Id
    @Column(name = "bet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int betId;

    @Column(name = "table_id")
    private int tableId;

    @Column(name = "bet_size")
    private int betSize;

    public int getBetId() {
        return betId;
    }

    public void setBetId(int betId) {
        this.betId = betId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getBetSize() {
        return betSize;
    }

    public void setBetSize(int betSize) {
        this.betSize = betSize;
    }
}
