package com.spalah.courses.projects.blackjack.model.dao;

import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public interface TableDao {
    List<TableType> getTableTypesVariants();

    Table createTable(TableType tableType, Account account);

    List<Command> getAvailableCommands(Table table);
}
