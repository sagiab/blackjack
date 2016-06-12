package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.exception.AllCardsWereUsedException;
import com.spalah.courses.projects.blackjack.exception.BetOutOfTypeRange;
import com.spalah.courses.projects.blackjack.model.dao.BetDao;
import com.spalah.courses.projects.blackjack.model.dao.TableDao;
import com.spalah.courses.projects.blackjack.model.dao.TableTypeDao;
import com.spalah.courses.projects.blackjack.model.dao.impl.BetDaoImpl;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableDaoImpl;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableTypeDaoImpl;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.Resultable;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.CardColor;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.CardType;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.Holder;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.game_ower.GameOver;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import com.spalah.courses.projects.blackjack.model.domain.table.TableGame;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

    //need to be autowired!!!!!!!
    private TableGameService tableGameService;


    public TableService(TableDao tableDao, TableTypeDao tableTypeDao, BetDao betDao) {
        this.tableDao = tableDao;
        this.tableTypeDao = tableTypeDao;
        this.betDao = betDao;
    }

    public TableService(){
        String PERSISTENCE_UNIT = "com.spalah.courses.projects.blackjack";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        TableDao tableDao = new TableDaoImpl(entityManagerFactory);
        TableTypeDao tableTypeDao = new TableTypeDaoImpl(entityManagerFactory);
        BetDao betDao = new BetDaoImpl(entityManagerFactory);
        this.tableDao = tableDao;
        this.tableTypeDao = tableTypeDao;
        this.betDao = betDao;
        tableGameService = new TableGameService();
        accountService = new AccountService();
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
    public Bet deal(String login, int betSize, long tableId) throws BetOutOfTypeRange, AccountException, AllCardsWereUsedException {
        TableType tableType = tableDao.getTable(tableId).getType();
        int minBet = tableType.getMinBetSize();
        int maxBet = tableType.getMaxBetSize();
        if (betSize >= minBet && betSize <= maxBet){
            //subtract betSize from playerBalance
            int updateSum = - betSize;
            accountService.updateAccountBalance(login, updateSum);
            Bet bet = betDao.addBet(tableDao.getTable(tableId), betSize);
            tableGameService.startFirstRound(tableId);
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

    public TableGameService getTableGameService() {
        return tableGameService;
    }

    public static void main(String[] args) {
        TableService tableService = new TableService();
        try {
            tableService.deal("Den@Den", 500, 1);

            TableGameService tableGameService = new TableGameService();
            Resultable result = null;
            while (!((result = tableGameService.hit(1L)) instanceof GameOver)) {
                System.out.println(result.printResult());
            }
            System.out.println(result.printResult());
        } catch (BetOutOfTypeRange betOutOfTypeRange) {
            betOutOfTypeRange.printStackTrace();
        } catch (AccountException e) {
            e.printStackTrace();
        } catch (AllCardsWereUsedException e) {
            e.printStackTrace();
        }
    }


}
