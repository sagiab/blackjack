package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.exception.AllCardsWereUsedException;
import com.spalah.courses.projects.blackjack.exception.BlackJackException;
import com.spalah.courses.projects.blackjack.exception.TableException;
import com.spalah.courses.projects.blackjack.model.dao.BetDao;
import com.spalah.courses.projects.blackjack.model.dao.TableDao;
import com.spalah.courses.projects.blackjack.model.dao.TableGameDao;
import com.spalah.courses.projects.blackjack.model.dao.TableTypeDao;
import com.spalah.courses.projects.blackjack.model.dao.impl.BetDaoImpl;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableDaoImpl;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableGameDaoImpl;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableTypeDaoImpl;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;
import com.spalah.courses.projects.blackjack.model.domain.commands.CommandType;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.Resultable;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.CardPack;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.CardType;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.cards.Holder;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.game_ower.GameOver;
import com.spalah.courses.projects.blackjack.model.domain.operation_result.game_ower.Winner;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima on 06.06.2016.
 */
public class TableGameService {
    private static final int MAX_SUM = 21;
    private static final int ACE_VALUE_WHEN_MORE_THAN_MAX_SUM = 1;
    private static final double BLACKJACK_MULTIPLY = 2.5;
    private TableGameDao tableGameDao;
    private CardPack cardPack;
    @Autowired
    private TableService tableService;
    @Autowired
    private AccountService accountService;


    //needs dependency injection!!!!!
    private BetDao betDao;


    public TableGameService(TableGameDao tableGameDao, BetDao betDao) {
        this.tableGameDao = tableGameDao;
        this.betDao = betDao;
        cardPack = new CardPack();
    }

    public TableGameService(){
        String PERSISTENCE_UNIT = "com.spalah.courses.projects.blackjack";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        TableDao tableDao = new TableDaoImpl(entityManagerFactory);
        TableTypeDao tableTypeDao = new TableTypeDaoImpl(entityManagerFactory);
        BetDao betDao = new BetDaoImpl(entityManagerFactory);
        tableService = new TableService(tableDao, tableTypeDao, betDao);
        TableGameDao tableGameDao = new TableGameDaoImpl(entityManagerFactory);
        this.tableGameDao = tableGameDao;
        this.betDao = betDao;
        cardPack = new CardPack();
    }

    public static void main(String[] args) {
        TableGameService tableGameService = new TableGameService();
        try {
            System.out.println(tableGameService.startFirstRound(1L));
            Resultable result = null;
            while (!((result = tableGameService.hit(1L)) instanceof GameOver)) {
                System.out.println(result.printResult());
            }
            System.out.println(result.printResult());
            //System.out.println(tableGameService.hit(1L));
        } catch (BlackJackException e) {
            e.printStackTrace();
        }
    }

    public List<Command> getAvailCommands(String login, Long tableId) throws AccountException, TableException {
        checkTableIdByLogin(login, tableId);
        List<Command> commands = new ArrayList<>();
        List<Card> cards = tableService.getUsedCards(tableId);
        if (cards.size() > 0) {
            commands.add(new Command(CommandType.HIT).available());
            commands.add(new Command(CommandType.BET).banned());
            commands.add(new Command(CommandType.STAND).available());
            commands.add(new Command(CommandType.EXIT).available());
        } else {
            commands.add(new Command(CommandType.HIT).available());
            commands.add(new Command(CommandType.BET).banned());
            commands.add(new Command(CommandType.STAND).available());
            commands.add(new Command(CommandType.EXIT).available());
        }
        return commands;
    }

    // Throw exception if table with id = tableId created by another Player
    private void checkTableIdByLogin(String login, Long tableId) throws AccountException, TableException {
        Table table = tableService.getTable(tableId);
        Account account = accountService.getAccount(login);
        if (!table.getPlayer().equals(account)) {
            throw new TableException("Sorry, but this table created by another player");
        }
    }

    public List<Card> startFirstRound(long tableId) throws AllCardsWereUsedException {
        List<Card> firstCards = new ArrayList<>();
        int playerSum = 0;

//        List<Card> usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        Card newPlayerCard = cardPack.nextCard(firstCards);
        playerSum += newPlayerCard.getCardType().getValue();
        newPlayerCard.setWhose(Holder.PLAYER);
        tableGameDao.addCard(newPlayerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newPlayerCard);

//        usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        Card newDialerCard = cardPack.nextCard(firstCards);
        newDialerCard.setWhose(Holder.DIALER);
        tableGameDao.addCard(newDialerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newDialerCard);


//        usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        newPlayerCard = cardPack.nextCard(firstCards);
        playerSum += newPlayerCard.getCardType().getValue();
        newPlayerCard.setWhose(Holder.PLAYER);
        tableGameDao.addCard(newPlayerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newPlayerCard);

//        usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        newDialerCard = cardPack.nextCard(firstCards);
        newDialerCard.setWhose(Holder.DIALER);
        tableGameDao.addCard(newDialerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newDialerCard);

        if (playerSum == 21) {
            //player won
        }
        System.out.println(playerSum);

        return firstCards;
    }

    /*
      * Returns the next card for this holder at this table. Return null if player has too many cards
     */
    public Resultable hit(long tableId) throws AllCardsWereUsedException, AccountException {
        List<Card> usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы

        Card newCard = cardPack.nextCard(usedCards);
        newCard.setWhose(Holder.PLAYER);
        tableGameDao.addCard(newCard, tableId);//добавляем эту карту в базу

        List<Card> playerCards = getHolderCards(Holder.PLAYER, usedCards);
        playerCards.add(newCard);
        int playerSum = calculateCardsSum(playerCards);
        System.out.println(Holder.PLAYER + "'s sum = " + playerSum);
        if (playerSum < MAX_SUM) {
            return newCard;
        } else if (playerSum == MAX_SUM) {
            //add win to balance
            String thisTablePlayerLogin = tableService.getTable(tableId).getPlayer().getLogin();
            Bet bet  = betDao.getBet(tableId);
            accountService.updateAccountBalance(thisTablePlayerLogin, bet.getBetSize() * BLACKJACK_MULTIPLY );
            return summarizeResults(Winner.PLAYER, usedCards, playerCards, playerSum);
        } else { //cardSum > MAX_SUM
            return summarizeResults(Winner.DIALER, usedCards, playerCards, playerSum);
        }
    }

    private GameOver summarizeResults(Winner winner, List<Card> usedCards, List<Card> playerCards, int playerSum){
        List<Card> dealerCards = getHolderCards(Holder.DIALER, usedCards);
        int dealerSum = calculateCardsSum(dealerCards);
        return new GameOver(winner, dealerCards, dealerSum, playerCards, playerSum);
    }

    private List<Card> getHolderCards(Holder holder, List<Card> usedCards) {
        List<Card> usedCardsCopy = new ArrayList<>(usedCards);
        usedCardsCopy.removeIf(p -> !p.getWhose().equals(holder));
        // delete those player cards which don't belong to this player type

        return usedCardsCopy;
    }

    private int calculateCardsSum(List<Card> holderCards) {
        int sumOfCards = 0;
        for (Card card : holderCards) {
            sumOfCards += card.getCardType().getValue();
        }

        if (sumOfCards > MAX_SUM) { // if some too much ACE's are counted as 1(if holder have them, otherwise he lose)
            int numberOfAces = 0;
            for (Card card : holderCards) {
                if (card.getCardType().equals(CardType.ACE)) {
                    numberOfAces++;
                }
            }
            if (numberOfAces > 0) {
                sumOfCards = sumOfCards - (CardType.ACE.getValue() * numberOfAces) + (ACE_VALUE_WHEN_MORE_THAN_MAX_SUM * numberOfAces);
            }
        }

        return sumOfCards;
    }
}
