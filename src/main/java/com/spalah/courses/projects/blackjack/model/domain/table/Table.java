package com.spalah.courses.projects.blackjack.model.domain.table;

import javax.persistence.*;

/**
 * Created by Dima on 08.06.2016.
 */
@Entity
@javax.persistence.Table(name = "Table-Player-bet")
public class Table {
    @Id
    @Column(name = "table_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tableId;

    @Column(name = "table_type_id")
    private long tableTypeId;

    @Column(name = "player_id")
    private int playerId;

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public long getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(long tableTypeId) {
        this.tableTypeId = tableTypeId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
