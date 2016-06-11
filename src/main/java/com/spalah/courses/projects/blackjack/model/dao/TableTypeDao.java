package com.spalah.courses.projects.blackjack.model.dao;

import com.spalah.courses.projects.blackjack.model.domain.table.TableType;

import java.util.List;

/**
 * Created by Dima on 09.06.2016.
 */
public interface TableTypeDao {
    List<TableType> getTableTypesVariants();

    TableType getTableTypeById(Long id);
}
