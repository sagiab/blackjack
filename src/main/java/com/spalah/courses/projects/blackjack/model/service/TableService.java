package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.exception.BetOutOfTypeRange;
import com.spalah.courses.projects.blackjack.model.dao.BetDao;
import com.spalah.courses.projects.blackjack.model.dao.TableDao;
import com.spalah.courses.projects.blackjack.model.dao.TableTypeDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.CardColor;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.CardType;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.Holder;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import com.spalah.courses.projects.blackjack.model.domain.table.TableGame;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class TableService {
    @Autowired
    private AccountService accountService;
    private TableDao tableDao;
    private TableTypeDao tableTypeDao;

    //no dependency injection !!!!!
    private BetDao betDao;


    public TableService(TableDao tableDao, TableTypeDao tableTypeDao, BetDao betDao) {
        this.tableDao = tableDao;
        this.tableTypeDao = tableTypeDao;
        this.betDao = betDao;
    }

    public List<TableType> getTableTypesVariants() {
        return tableTypeDao.getTableTypesVariants();
    }

    public Table getTable(Long tableId) {
        return tableDao.getTable(tableId);
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
    public Bet deal(int betSize, long tableId) throws BetOutOfTypeRange {
        TableType tableType = tableDao.getTable(tableId).getType();
        int minBet = tableType.getMinBetSize();
        int maxBet = tableType.getMinBetSize();
        Bet bet = null;
        if (betSize >= minBet && betSize <= maxBet){
            bet = betDao.addBet(tableDao.getTable(tableId), betSize);
            return bet;
        }
        throw new BetOutOfTypeRange("Bet should be between " + minBet + " and " + maxBet);
    }

    public List<Card> getUsedCards(long tableId) {
        List<TableGame> steps = tableDao.getSteps(tableId);

        //get Cards from steps list, which are written as "<CardType><.><CardColor>'
        List<Card> usedCards = new ArrayList<>();
        for (TableGame tableGame : steps){
            Holder whoseCard = Holder.valueOf(tableGame.getCardsHolder());
            String cardStr = tableGame.getCards();
            String[] cardParts = cardStr.split("\\."); //экранирование точки
            CardType cardType = CardType.valueOf(cardParts[0]);
            CardColor cardColor = CardColor.valueOf(cardParts[1]);
            usedCards.add(new Card(cardColor, cardType, whoseCard));
        }

        return usedCards;
    }
}
