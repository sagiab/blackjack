package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.model.dao.TableDao;
import com.spalah.courses.projects.blackjack.model.dao.TableTypeDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import com.spalah.courses.projects.blackjack.model.domain.table.TableBetRange;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class TableService{
    @Autowired
    private AccountService accountService;
    private TableDao tableDao;
    private TableTypeDao tableTypeDao;


    public TableService(TableDao tableDao, TableTypeDao tableTypeDao){
        this.tableDao = tableDao;
        this.tableTypeDao = tableTypeDao;
    }

    public Table createTable(Long tableTypeId, String login) throws AccountException {
        TableType tableType = tableTypeDao.getTableTypeById(tableTypeId);
        Account account = accountService.getAccount(login);
        return tableDao.createTable(tableType, account);
    }

    /*
        Make player's bet for specific table
        return Bet which was accepted and null if bet < min table type's bet size or bet > max table type's bet size
     */
    public Bet makeBet(int betSize, long tableId){
        TableBetRange tableBetRange = tableDao.getTableBetRange(tableId);
        if (betSize >= tableBetRange.getMinBet() && betSize <= tableBetRange.getMaxBet()){
            Bet bet = new Bet();
            bet.setTable();
            betDao.addBe
        }
    }

    public List<Card> getUsedCards(long tableId){
        return tableDao.getUsedCards(tableId);
    }

    public List<TableType> getTableTypesVariants() {
        return tableTypeDao.getTableTypesVariants();
    }

    public static void main(String[] args) {

    }
}
