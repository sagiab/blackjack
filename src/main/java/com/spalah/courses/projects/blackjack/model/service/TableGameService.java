package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.*;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardPack;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;
import com.spalah.courses.projects.blackjack.model.domain.commands.CommandType;
import com.spalah.courses.projects.blackjack.model.domain.table.Holder;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima on 06.06.2016.
 */
public class TableGameService {

    private static final int MAX_SUM = 21;
    private CardPack cardPack;
    @Autowired
    private TableService tableService;
    @Autowired
    private AccountService accountService;


    public TableGameService() {
        cardPack = new CardPack();
    }

    public static void main(String[] args) {
        TableGameService tableService = new TableGameService();
        try {
            System.out.println(tableService.getCard(Holder.DIALER, 1L));
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

    public Card getCard(Holder holder, long tableId) throws AllCardsWereUsedException, PlayerCantHitAnymoreException {
        List<Card> usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        List<Card> holderCards = getHolderCards(holder, usedCards);
        //System.out.println(holderCards);
        //берем карты игрока или дилераиз базы

        int playerSum = sumCards(holderCards); //считаем сумму карт нужного игрока,вместо -999999 - id
        //System.out.println(holder + "'s sum = " + playerSum);
        Card card = null;
        if (playerSum < MAX_SUM) {
            card = cardPack.nextCard(usedCards);
            //добавляем эту карту в базу
        }
        if (card != null) {
            return card;
        } else {
            throw new PlayerCantHitAnymoreException();
        }
    }

    private List<Card> getHolderCards(Holder holder, List<Card> usedCards) {
        List<Card> usedCardsCopy = new ArrayList<>(usedCards);
        usedCardsCopy.removeIf(p -> !p.getWhose().equals(holder));
        // delete those player cards which don't belong to this player type

        return usedCardsCopy;
    }

    private int sumCards(List<Card> holderCards) {
        int sumOfCards = 0;
        for (Card card : holderCards) {
            sumOfCards += card.getCardType().getValue();
        }

        return sumOfCards;
    }
}
