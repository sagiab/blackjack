package com.spalah.courses.projects.blackjack.service;

import com.spalah.courses.projects.blackjack.exception.*;
import com.spalah.courses.projects.blackjack.model.dao.BetDao;
import com.spalah.courses.projects.blackjack.model.dao.TableGameDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.bet.BetWinMultiply;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardPack;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardType;
import com.spalah.courses.projects.blackjack.model.domain.cards.Holder;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;
import com.spalah.courses.projects.blackjack.model.domain.commands.CommandType;
import com.spalah.courses.projects.blackjack.model.domain.gameover.GameOutcome;
import com.spalah.courses.projects.blackjack.model.domain.gameover.GameOver;
import com.spalah.courses.projects.blackjack.model.domain.response.TableGameStep;
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
        Bet bet = getBet(tableId);
        if (bet != null) {
            commands.add(new Command(CommandType.HIT).available());
            commands.add(new Command(CommandType.BET).banned());
            commands.add(new Command(CommandType.STAND).available());
            commands.add(new Command(CommandType.EXIT).available());
        } else {
            commands.add(new Command(CommandType.HIT).banned());
            commands.add(new Command(CommandType.BET).available());
            commands.add(new Command(CommandType.STAND).banned());
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
            throw new TableException("Sorry, but this table created by another player");
        }
    }

    public TableGameStep startFirstRound(String login, long tableId)
            throws AllCardsWereUsedException, GameOverException {
        TableGameStep tableGameStep = new TableGameStep();
        List<Card> firstCards = new ArrayList<>();
        int playerSum = 0;

        Card newPlayerCard = cardPack.nextCard(firstCards);
        playerSum += newPlayerCard.getCardType().getValue();
        newPlayerCard.setWhose(Holder.PLAYER);
        addCard(newPlayerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newPlayerCard);

        Card newDialerCard = cardPack.nextCard(firstCards);
        newDialerCard.setWhose(Holder.DIALER);
        addCard(newDialerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newDialerCard);

        newPlayerCard = cardPack.nextCard(firstCards);
        playerSum += newPlayerCard.getCardType().getValue();
        newPlayerCard.setWhose(Holder.PLAYER);
        addCard(newPlayerCard, tableId);//добавляем эту карту в базу
        firstCards.add(newPlayerCard);

        newDialerCard = cardPack.nextCard(firstCards);
        newDialerCard.setWhose(Holder.DIALER);
        addCard(newDialerCard, tableId);//добавляем эту карту в базу
//        firstCards.add(newDialerCard);

        if (playerSum == MAX_SUM) {
            throw new GameOverException(login, tableId);
        }
        System.out.println(playerSum);
        List<Card> playerCards = getHolderCards(Holder.PLAYER, firstCards);
        List<Card> dealerCards = getHolderCards(Holder.DIALER, firstCards);

        tableGameStep.setDealerCards(dealerCards);
        tableGameStep.setPlayerCards(playerCards);
        tableGameStep.setPlayerSum(calculateCardsSum(playerCards));

        return tableGameStep;
    }

    private void addCard(Card card, long tableId) {
        TableGame tableGame = new TableGame();
        Bet tableBet = getBet(tableId);
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
            throws BetException, AccountException, AllCardsWereUsedException, TableException {
        Table table = tableService.getTable(tableId);
        checkTableIdByAccount(table, login);
        Account account = table.getPlayer();

        Bet bet = getBet(table.getTableId());
        isBetExist(bet);

        if (isGoodBet(table, betSize)) {
            //subtract betSize from playerBalance
            int updateSum = -betSize;
            accountService.updateAccountBalance(account, updateSum);
            betDao.addBet(tableId, betSize);
        }
    }

    private boolean isGoodBet(Table table, int betSize) throws BetException {
        TableType tableType = table.getType();
        int minBet = tableType.getMinBetSize();
        int maxBet = tableType.getMaxBetSize();

        if (betSize < minBet || betSize > maxBet) {
            throw new BetException("Bet should be between " + minBet + " and " + maxBet);
        } else return true;
    }

    private boolean isBetExist(Bet bet) throws BetException {
        if (bet != null) throw new BetException("You already have one bet ");
        else return false;
    }

    Bet getBet(Long tableId) {
        Bet bet;
        try {
            bet = betDao.getBet(tableId);
        } catch (NoResultException e) {
            bet = null;
        }
        return bet;
    }

    public Bet deleteBet(String login, long tableId) throws AccountException, TableException {
        checkTableIdByLogin(login, tableId);
        return deleteBet(tableId);
    }

    public Bet deleteBet(long tableId) {
        return betDao.deleteBet(tableId);
    }

    /*
      * Returns the next card for this holder at this table. Return null if player has too many cards
     */
    public TableGameStep hit(String login, long tableId)
            throws AccountException, TableException, AllCardsWereUsedException, GameOverException {
        checkTableIdByLogin(login, tableId);
        TableGameStep tableGameStep = new TableGameStep();

        List<Card> usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        List<Card> dealerCards = getHolderCards(Holder.DIALER, usedCards);

        Card newPlayerCard = cardPack.nextCard(usedCards);
        newPlayerCard.setWhose(Holder.PLAYER);
        addCard(newPlayerCard, tableId);//добавляем эту карту в базу

        List<Card> playerCards = getHolderCards(Holder.PLAYER, usedCards);
        playerCards.add(newPlayerCard);
        int playerSum = calculateCardsSum(playerCards);

        dealerCards.remove(dealerCards.size() - 1);
        tableGameStep.setPlayerSum(playerSum);
        tableGameStep.setPlayerCards(playerCards);
        tableGameStep.setDealerCards(dealerCards);

        if (playerSum < MAX_SUM) {
            return tableGameStep;
        } else {
            throw new GameOverException(login, tableId);
        }
    }

    public GameOver stand(String login, long tableId)
            throws AllCardsWereUsedException, AccountException, TableException {
        checkTableIdByLogin(login, tableId);
        List<Card> usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        List<Card> dealerCards = getHolderCards(Holder.DIALER, usedCards);
        List<Card> playerCards = getHolderCards(Holder.PLAYER, usedCards);
        int playerSum = calculateCardsSum(playerCards);

        if (playerSum <= MAX_SUM) {
            while ((calculateCardsSum(dealerCards)) < 17) {
                Card newDealerCard = cardPack.nextCard(usedCards);
                newDealerCard.setWhose(Holder.DIALER);
                addCard(newDealerCard, tableId);//добавляем эту карту в базу
                dealerCards.add(newDealerCard);
            }
        }

        GameOver gameOver = compareDialerAndPlayerResults(playerCards, dealerCards);
        GameOutcome gameOutcome = gameOver.getGameOutcome();
        if (gameOutcome.equals(GameOutcome.PLAYER_WON)) {
            if (playerCards.size() == 2 && playerSum == MAX_SUM) {
                gameOver.setProfit(tableService.spreadCash(tableId, BetWinMultiply.BLACKJACK_MULTIPLY));
            } else {
                gameOver.setProfit(tableService.spreadCash(tableId, BetWinMultiply.DOUBLE_MULTIPLY)); // return bet * 2
            }
        } else if (gameOutcome.equals(GameOutcome.DRAW)) {
            gameOver.setProfit(tableService.spreadCash(tableId, BetWinMultiply.DRAW_MULTIPLY)); // just return bet
        } else {
            deleteBet(tableId);
        }
        //if GameOutcome.DIALER_WON - balance stays the same
        return gameOver;
    }

    private GameOver compareDialerAndPlayerResults(List<Card> playerCards, List<Card> dealerCards) {
        int playerSum = calculateCardsSum(playerCards);
        int dealerSum = calculateCardsSum(dealerCards);

        GameOutcome gameOutcome;
        if (playerSum > MAX_SUM) {
            gameOutcome = GameOutcome.DIALER_WON;
        } else {
            if (dealerSum > MAX_SUM) {
                gameOutcome = GameOutcome.PLAYER_WON;
            } else { // compareSums
                if (playerSum < dealerSum) {
                    gameOutcome = GameOutcome.DIALER_WON;
                } else if (playerSum > dealerSum) {
                    gameOutcome = GameOutcome.PLAYER_WON;
                } else {
                    gameOutcome = GameOutcome.DRAW;
                }
            }
        }

        return new GameOver(gameOutcome, dealerCards, dealerSum, playerCards, playerSum);
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
                for (int i = 0; i < numberOfAces; i++) {
                    if (sumOfCards > MAX_SUM) sumOfCards -= CardType.ACE.getValue() - ACE_VALUE_WHEN_MORE_THAN_MAX_SUM;
                }
//                sumOfCards = sumOfCards - (CardType.ACE.getValue() * numberOfAces)
//                        + (ACE_VALUE_WHEN_MORE_THAN_MAX_SUM * numberOfAces);
            }
        }

        return sumOfCards;
    }
}
