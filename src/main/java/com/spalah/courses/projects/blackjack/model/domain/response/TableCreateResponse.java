package com.spalah.courses.projects.blackjack.model.domain.response;

import com.spalah.courses.projects.blackjack.model.domain.table.Table;

/**
 * @author Denis Loshkarev on 10.06.2016.
 */
public class TableCreateResponse {
    private Long tableId;

    public TableCreateResponse(Table table) {
        this.tableId = table.getTableId();
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }
}
