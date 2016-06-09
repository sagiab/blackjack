package com.spalah.courses.projects.blackjack.model.domain.bet;

import com.spalah.courses.projects.blackjack.model.domain.table.TableGame;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Dima on 08.06.2016.
 */
@Entity
@javax.persistence.Table(name = "bet_player_communication")
public class Bet {
    @Id
    @Column(name = "bet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long betId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private com.spalah.courses.projects.blackjack.model.domain.table.Table table;

    @Column(name = "bet_size")
    private int betSize;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bet")
    private List<TableGame> steps;

    public long getBetId() {
        return betId;
    }

    public void setBetId(long betId) {
        this.betId = betId;
    }

    public com.spalah.courses.projects.blackjack.model.domain.table.Table getTable() {
        return table;
    }

    public void setTable(com.spalah.courses.projects.blackjack.model.domain.table.Table table) {
        this.table = table;
    }

    public int getBetSize() {
        return betSize;
    }

    public void setBetSize(int betSize) {
        this.betSize = betSize;
    }
}
