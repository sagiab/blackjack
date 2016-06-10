package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AllCardsWereUsedException;
import com.spalah.courses.projects.blackjack.exception.BlackJackException;
import com.spalah.courses.projects.blackjack.exception.PlayerCantHitAnymoreException;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableDaoImpl;
import com.spalah.courses.projects.blackjack.model.dao.impl.TableTypeDaoImpl;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardPack;
import com.spalah.courses.projects.blackjack.model.domain.table.PlayerType;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima on 06.06.2016.
 */
public class TableGameService {

    private static final int MAX_SUM = 21;
    private CardPack cardPack;
    private TableService tableService;


    public TableGameService() {
        cardPack = new CardPack();
        //пока так. НО ИСПРАВИТЬ c autowired !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        String PERSISTENCE_UNIT = "com.spalah.courses.projects.blackjack";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        tableService = new TableService(new TableDaoImpl(entityManagerFactory), new TableTypeDaoImpl(entityManagerFactory));

    }


    public Card getCard(PlayerType playerType, long tableId) throws AllCardsWereUsedException, PlayerCantHitAnymoreException {
        List<Card> usedCards = tableService.getUsedCards(tableId); //берем все использованные карты из базы
        List<Card> playerOrDialerCards = getPlayerOrDillerCards(playerType, usedCards);
        //System.out.println(playerOrDialerCards);
        //берем карты игрока или дилераиз базы

        int playerSum = sumCards(playerOrDialerCards); //считаем сумму карт нужного игрока,вместо -999999 - id
        //System.out.println(playerType + "'s sum = " + playerSum);
        Card card = null;
        if (playerSum < MAX_SUM) {
            card = cardPack.nextCard(usedCards);
            //добавляем эту карту в базу
        }
        if (card != null){
            return card;
        }
        else {
            throw new PlayerCantHitAnymoreException();
        }
    }

    private List<Card> getPlayerOrDillerCards(PlayerType playerType, List<Card> usedCards) {
        List<Card> usedCardsCopy = new ArrayList<>(usedCards);
        Predicate predicate = new Predicate<PlayerType>();
        predicate.setPredicate(playerType);
        usedCardsCopy.removeIf(predicate);

        return usedCardsCopy;
    }


    private int sumCards(List<Card> playerOrDialerCards) {
        int sumOfCards = 0;
        for (Card card : playerOrDialerCards) {
            sumOfCards += card.getCardType().getValue();
        }

        return sumOfCards;
    }

    class Predicate<PlayerType> implements java.util.function.Predicate<PlayerType>{
        PlayerType objectToDelete;

        void setPredicate(PlayerType predicate){
            objectToDelete = predicate;
        }

        @Override
        public boolean test(Object o) {
            if (objectToDelete == null) throw new NullPointerException("Predicate was not set");
            Card card = (Card) o;
            if (!card.getWhose().equals(objectToDelete)){
                return true;
            }
            else{
                return false;
            }
        }
    }

    public static void main(String[] args) {
        TableGameService tableService = new TableGameService();
        try {
            System.out.println(tableService.getCard(PlayerType.Player, 1L));
        } catch (BlackJackException e) {
            e.printStackTrace();
        }
    }
}
