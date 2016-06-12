package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.exception.AllCardsWereUsedException;
import com.spalah.courses.projects.blackjack.exception.BetOutOfTypeRange;
import com.spalah.courses.projects.blackjack.exception.TableException;
import com.spalah.courses.projects.blackjack.model.dao.BetDao;
import com.spalah.courses.projects.blackjack.model.dao.TableGameDao;
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
import com.spalah.courses.projects.blackjack.model.domain.table.TableGame;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima on 06.06.2016.
 */
public class TableGameService {
    private static final int MAX_SUM = 21;
    private static final int ACE_VALUE_WHEN_MORE_THAN_MAX_SUM = 1;
    private static final double BLACKJACK_MULTIPLY = 2.5;

    @Autowired
    private TableService tableService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TableGameDao tableGameDao;
    @Autowired
    private BetDao betDao;

    private CardPack cardPack;

    public TableGameService() {
        cardPack = new CardPack();
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
        checkTableIdByAccount(table, login);
    }

    private void checkTableIdByAccount(Table table, String login) throws AccountException, TableException {
        Account account = accountService.getAccount(login);
        if (!table.getPlayer().equals(account)) {
            System.out.println(account + " = " + table.getPlayer());
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
        addCard(newPlayerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newPlayerCard);

//        usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        Card newDialerCard = cardPack.nextCard(firstCards);
        newDialerCard.setWhose(Holder.DIALER);
        addCard(newDialerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newDialerCard);


//        usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        newPlayerCard = cardPack.nextCard(firstCards);
        playerSum += newPlayerCard.getCardType().getValue();
        newPlayerCard.setWhose(Holder.PLAYER);
        addCard(newPlayerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newPlayerCard);

//        usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        newDialerCard = cardPack.nextCard(firstCards);
        newDialerCard.setWhose(Holder.DIALER);
        addCard(newDialerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newDialerCard);

        if (playerSum == 21) {
            //player won
        }
        System.out.println(playerSum);

        return firstCards;
    }

    public void addCard(Card card, long tableId) {
        TableGame tableGame = new TableGame();
        Bet tableBet = betDao.getBet(tableId);
        tableGame.setBet(tableBet);
        tableGame.setCards(card.toString());
        tableGame.setCardsHolder(card.getWhose().toString());
        tableGameDao.addCard(tableGame);
    }

    /*
        Make player's bet for specific table
        return Bet which was accepted and null if bet < min table type's bet size or bet > max table type's bet size
     */
    public void createBet(String login, int betSize, long tableId)
            throws BetOutOfTypeRange, AccountException, AllCardsWereUsedException, TableException {
        Table table = tableService.getTable(tableId);
        checkTableIdByAccount(table, login);
        Account account = table.getPlayer();
        if (isGoodBet(table, betSize)) {
            //subtract betSize from playerBalance
            int updateSum = -betSize;
            accountService.updateAccountBalance(account, updateSum);
            betDao.addBet(tableId, betSize);
        }
    }

    private boolean isGoodBet(Table table, int betSize) throws BetOutOfTypeRange {
        TableType tableType = table.getType();
        int minBet = tableType.getMinBetSize();
        int maxBet = tableType.getMaxBetSize();
        Bet bet;
        try {
            bet = betDao.getBet(table.getTableId());
        } catch (NoResultException e) {
            bet = null;
        }
        if (betSize < minBet || betSize > maxBet) {
            throw new BetOutOfTypeRange("Bet should be between " + minBet + " and " + maxBet);
        } else if (bet != null) {
            throw new BetOutOfTypeRange("You already have one bet ");
        } else return true;
    }

    public Bet deleteBet(String login, long tableId) throws AccountException, TableException {
        checkTableIdByLogin(login, tableId);
        return betDao.deleteBet(tableId);
    }

    /*
      * Returns the next card for this holder at this table. Return null if player has too many cards
     */
    public Resultable hit(long tableId) throws AllCardsWereUsedException, AccountException {
        List<Card> usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы

        Card newCard = cardPack.nextCard(usedCards);
        newCard.setWhose(Holder.PLAYER);
        addCard(newCard, tableId);//добавляем эту карту в базу

        List<Card> playerCards = getHolderCards(Holder.PLAYER, usedCards);
        playerCards.add(newCard);
        int playerSum = calculateCardsSum(playerCards);
        System.out.println(Holder.PLAYER + "'s sum = " + playerSum);
        if (playerSum < MAX_SUM) {
            return newCard;
        } else if (playerSum == MAX_SUM) {
            //add win to balance
            Bet bet = betDao.getBet(tableId);
            Account account = tableService.getTable(tableId).getPlayer();
            accountService.updateAccountBalance(account, bet.getBetSize() * BLACKJACK_MULTIPLY);
            return summarizeResults(Winner.PLAYER, usedCards, playerCards, playerSum);
        } else { //cardSum > MAX_SUM
            return summarizeResults(Winner.DIALER, usedCards, playerCards, playerSum);
        }
    }

    private GameOver summarizeResults(Winner winner, List<Card> usedCards, List<Card> playerCards, int playerSum) {
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
                sumOfCards = sumOfCards - (CardType.ACE.getValue() * numberOfAces)
                        + (ACE_VALUE_WHEN_MORE_THAN_MAX_SUM * numberOfAces);
            }
        }

        return sumOfCards;
    }
}
