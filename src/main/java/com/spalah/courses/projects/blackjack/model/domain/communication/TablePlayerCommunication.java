package com.spalah.courses.projects.blackjack.model.domain.communication;

import javax.persistence.*;

/**
 * Created by Dima on 08.06.2016.
 */
@Entity
@Table(name = "Table-Player-communication")
public class TablePlayerCommunication {
    @Id
    @Column(name = "table_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tableId;

    @Column(name = "table_type_id")
    private int tableTypeId;

    @Column(name = "player_id")
    private int playerId;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(int tableTypeId) {
        this.tableTypeId = tableTypeId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
