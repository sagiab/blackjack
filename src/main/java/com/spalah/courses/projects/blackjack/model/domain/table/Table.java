package com.spalah.courses.projects.blackjack.model.domain.table;

import com.spalah.courses.projects.blackjack.model.domain.account.Account;

import javax.persistence.*;

/**
 * Created by Dima on 08.06.2016.
 */
@Entity
@javax.persistence.Table(name = "table_player_communication")
public class Table {
    @Id
    @Column(name = "table_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tableId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_type_id")
    private TableType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Account player;

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }


    public TableType getTableTypeId() {
        return type;
    }

    public void setTableTypeId(TableType tableType) {
        this.type = tableType;
    }

    public Account getPlayer() {
        return player;
    }

    public void setPlayerId(Account playerId) {
        this.player = playerId;
    }
}
