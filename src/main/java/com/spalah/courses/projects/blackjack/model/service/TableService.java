package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.model.dao.TableDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class TableService implements TableDao{

    @Override
    public List<TableType> getTableTypesVariants() {
        return null;
    }

    @Override
    public Table createTable(TableType tableType, Account account) {
        return null;
    }

    @Override
    public List<Command> getAvailableCommands(Table table) {
        return null;
    }
}
