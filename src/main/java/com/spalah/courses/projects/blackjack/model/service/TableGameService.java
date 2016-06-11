package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.exception.AllCardsWereUsedException;
import com.spalah.courses.projects.blackjack.exception.BlackJackException;
import com.spalah.courses.projects.blackjack.exception.TableException;
import com.spalah.courses.projects.blackjack.model.dao.TableGameDao;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableGameDaoImpl;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardPack;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardType;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;
import com.spalah.courses.projects.blackjack.model.domain.commands.CommandType;
import com.spalah.courses.projects.blackjack.model.domain.table.Holder;
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
    private TableGameDao tableGameDao;
    private CardPack cardPack;
    @Autowired
    private TableService tableService;
    @Autowired
    private AccountService accountService;


    public TableGameService(TableGameDao tableGameDao) {
        this.tableGameDao = tableGameDao;
        cardPack = new CardPack();
    }

    public static void main(String[] args) {
        String PERSISTENCE_UNIT = "com.spalah.courses.projects.blackjack";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        TableGameDao tableGameDao = new TableGameDaoImpl(entityManagerFactory);
        TableGameService tableService = new TableGameService(tableGameDao);
        try {
            System.out.println(tableService.startFirstRound(1L));
            Card card = null;
            while ((card = tableService.getPlayerCard(1L)) != null) {
                System.out.println(card);
            }
            //System.out.println(tableService.getPlayerCard(1L));
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

        List<Card> usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        Card newPlayerCard = cardPack.nextCard(usedCards);
        playerSum += newPlayerCard.getCardType().getValue();
        tableGameDao.addCard(newPlayerCard, tableId, Holder.PLAYER);//добавляем эту карту в базу
        newPlayerCard.setWhose(Holder.PLAYER);
        firstCards.add(newPlayerCard);

        usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        Card newDialerCard = cardPack.nextCard(usedCards);
        tableGameDao.addCard(newDialerCard, tableId, Holder.DIALER);//добавляем эту карту в базу
        newDialerCard.setWhose(Holder.DIALER);
        firstCards.add(newDialerCard);


        usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        newPlayerCard = cardPack.nextCard(usedCards);
        playerSum += newPlayerCard.getCardType().getValue();
        tableGameDao.addCard(newPlayerCard, tableId, Holder.PLAYER);//добавляем эту карту в базу
        newPlayerCard.setWhose(Holder.PLAYER);
        firstCards.add(newPlayerCard);

        usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        newDialerCard = cardPack.nextCard(usedCards);
        tableGameDao.addCard(newDialerCard, tableId, Holder.DIALER);//добавляем эту карту в базу
        newDialerCard.setWhose(Holder.DIALER);
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
    public Card getPlayerCard(long tableId) throws AllCardsWereUsedException {
        List<Card> usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        //System.out.println(holderCards);

        Card newCard = cardPack.nextCard(usedCards);
        tableGameDao.addCard(newCard, tableId, Holder.PLAYER);//добавляем эту карту в базу

        List<Card> playerCards = getHolderCards(Holder.PLAYER, usedCards);
        playerCards.add(newCard);
        int cardSum = calculateCardsSum(playerCards);
        System.out.println(Holder.PLAYER + "'s sum = " + cardSum);
        if (cardSum < MAX_SUM) {
            return newCard;
        } else if (cardSum == MAX_SUM) {
            System.out.println("Player won");
            //player won
            return null;
        } else { //cardSum > MAX_SUM
            System.out.println("Player lost");
            //playerLost
            return null;
        }
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
