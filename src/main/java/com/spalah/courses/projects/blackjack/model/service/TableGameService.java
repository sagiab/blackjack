package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AllCardsWereUsedException;
import com.spalah.courses.projects.blackjack.exception.BlackJackException;
import com.spalah.courses.projects.blackjack.model.dao.TableGameDao;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableDaoImpl;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableGameDaoImpl;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableTypeDaoImpl;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardPack;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardType;
import com.spalah.courses.projects.blackjack.model.domain.table.Holder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima on 06.06.2016.
 */
public class TableGameService {
    private TableGameDao tableGameDao;

    private static final int MAX_SUM = 21;
    private static final int ACE_VALUE_WHEN_MORE_THAN_MAX_SUM = 1;
    private CardPack cardPack;
    private TableService tableService;


    public TableGameService(TableGameDao tableGameDao) {
        this.tableGameDao = tableGameDao;
        cardPack = new CardPack();
        //пока так. НО ИСПРАВИТЬ c autowired !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        String PERSISTENCE_UNIT = "com.spalah.courses.projects.blackjack";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        tableService = new TableService(new TableDaoImpl(entityManagerFactory), new TableTypeDaoImpl(entityManagerFactory));

    }


    /*
      * Returns the next card for this holder at this table. Return null if player has too many cards
     */
    public Card getCard(Holder holder, long tableId) throws AllCardsWereUsedException {
        List<Card> usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        List<Card> holderCards = getHolderCards(holder, usedCards);
        //System.out.println(holderCards);
        //берем карты игрока или дилераиз базы

        int cardSum = calculateCardsSum(holderCards);
        //System.out.println(holder + "'s sum = " + cardSum);
        Card card = null;
        if (cardSum < MAX_SUM) {
            card = cardPack.nextCard(usedCards);
            tableGameDao.addCard(card, tableId, holder);//добавляем эту карту в базу
        }

        return card;
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

        if (sumOfCards >= MAX_SUM) {
            if (holderCards.stream().anyMatch(c -> c.getCardType().equals(CardType.ACE))) {
                sumOfCards = sumOfCards - CardType.ACE.getValue() + ACE_VALUE_WHEN_MORE_THAN_MAX_SUM;
            }
        }

        return sumOfCards;
    }

    public static void main(String[] args) {
        String PERSISTENCE_UNIT = "com.spalah.courses.projects.blackjack";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        TableGameDao tableGameDao = new TableGameDaoImpl(entityManagerFactory);
        TableGameService tableService = new TableGameService(tableGameDao);
        try {
            System.out.println(tableService.getCard(Holder.DIALER, 1L));
        } catch (BlackJackException e) {
            e.printStackTrace();
        }
    }
}
