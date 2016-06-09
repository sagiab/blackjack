package com.spalah.courses.projects.blackjack.model.domain.table;

import javax.persistence.*;

/**
 * Created by Dima on 08.06.2016.
 */
@Entity
@javax.persistence.Table(name = "table-player-communication")
public class Table {
    @Id
    @Column(name = "table_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tableId;

    private TableType tableType;

    @Column(name = "player_id")
    private int playerId;

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_type_id", nullable = false)
    public TableType getTableTypeId() {
        return tableType;
    }

    public void setTableTypeId(TableType tableType) {
        this.tableType = tableType;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
