package com.spalah.courses.projects.blackjack.service;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.exception.TableException;
import com.spalah.courses.projects.blackjack.model.dao.TableDao;
import com.spalah.courses.projects.blackjack.model.dao.TableTypeDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardColor;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardType;
import com.spalah.courses.projects.blackjack.model.domain.cards.Holder;
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
    @Autowired
    private TableGameService tableGameService;
    @Autowired
    private TableDao tableDao;
    @Autowired
    private TableTypeDao tableTypeDao;

    public TableService() {
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

    public double spreadCash(long tableId, double multiply) throws AccountException, TableException {
        Bet bet = tableGameService.getBet(tableId);
        Account account = getTable(tableId).getPlayer();
        double profit = bet.getBetSize() * multiply;
        accountService.updateAccountBalance(account, profit);
        tableGameService.deleteBet(tableId);
        return profit;
    }

    public List<Card> getUsedCards(long tableId) {
        List<TableGame> steps = tableDao.getSteps(tableId);

        //get Cards from steps list, which are written as "<CardType><.><CardColor>'
        List<Card> usedCards = new ArrayList<>();
        for (TableGame tableGame : steps) {
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
