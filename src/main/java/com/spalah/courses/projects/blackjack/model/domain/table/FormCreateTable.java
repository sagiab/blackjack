package com.spalah.courses.projects.blackjack.model.domain.table;

/**
 * @author Denis Loshkarev on 10.06.2016.
 */
public class FormCreateTable {
    long tableTypeId;

    public FormCreateTable() {
    }

    public long getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(long tableTypeId) {
        this.tableTypeId = tableTypeId;
    }
}
