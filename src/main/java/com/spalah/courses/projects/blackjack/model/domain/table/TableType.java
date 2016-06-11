package com.spalah.courses.projects.blackjack.model.domain.table;

import javax.persistence.*;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
@Entity
@javax.persistence.Table(name = "table_type")
public class TableType {
    @Id
    @Column(name = "table_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "min_bet")
    private int minBetSize;

    @Column(name = "max_bet")
    private int maxBetSize;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMinBetSize() {
        return minBetSize;
    }

    public void setMinBetSize(int minBetSize) {
        this.minBetSize = minBetSize;
    }

    public int getMaxBetSize() {
        return maxBetSize;
    }

    public void setMaxBetSize(int maxBetSize) {
        this.maxBetSize = maxBetSize;
    }

    @Override
    public String toString() {
        return "TableType{" +
                "id=" + id +
                ", minBetSize=" + minBetSize +
                ", maxBetSize=" + maxBetSize +
                '}';

    }
}
