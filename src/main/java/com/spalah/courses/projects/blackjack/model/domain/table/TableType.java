package com.spalah.courses.projects.blackjack.model.domain.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tableType")
    private Set<Table> tablesWithThisType = new HashSet<>(0);


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

    public Set<Table> getTablesWithThisType() {
        return tablesWithThisType;
    }

    public void setTablesWithThisType(Set<Table> tablesWithThisType) {
        this.tablesWithThisType = tablesWithThisType;
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
