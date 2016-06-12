package com.spalah.courses.projects.blackjack.model.domain.table;

import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_type_id")
    private TableType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Account player;

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public TableType getType() {
        return type;
    }

    public void setType(TableType type) {
        this.type = type;
    }

    public Account getPlayer() {
        return player;
    }

    public void setPlayer(Account player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tableId", tableId)
                .append("type", type)
                .append("player", player)
                .toString();
    }
}
