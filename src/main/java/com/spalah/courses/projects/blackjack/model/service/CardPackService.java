package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AllCardsWereUsedException;
import com.spalah.courses.projects.blackjack.model.dao.CardPackDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardPack;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 * @author Dima Zasuha on 05.06.2016.
 */
public class CardPackService implements CardPackDao{
    private TableGameService tableGameService;
    private CardPack cardPack;
    private static final int MAX_SUM = 21;

    public CardPackService(){
        cardPack = new CardPack();
        tableGameService = new TableGameService();
    }

    public void getCard(Account account){
        int playerSum = sumCards(account.getLogin()); //считаем сумму карт нужного игрока,вместо -999999 - id
        if (playerSum < MAX_SUM) {
            List<Card> usedCards = null; //берем все использованные карты из базы
            try {
                Card card = cardPack.nextCard(usedCards);
                //добавляем эту карту в базу
            } catch (AllCardsWereUsedException e) {
                e.printStackTrace();
            }
        }
    }


    private int sumCards(String playerId){
        List<Card> playerOrDialerCard = tableGameService.getPlayerCards(playerId); //берем карты игрока из базы

        int sumOfCards = 0;
        for (Card card : playerOrDialerCard){
            sumOfCards += card.getCardType().getValue();
        }

        return sumOfCards;
    }



}
