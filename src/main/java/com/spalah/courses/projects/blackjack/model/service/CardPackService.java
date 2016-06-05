package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.model.domain.cards.Card;
import com.spalah.courses.projects.blackjack.model.domain.cards.CardPack;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 * @author Denis Loshkarev on 05.06.2016.
 */
public class CardPackService {
    private CardPack cardPack;

    public CardPackService(){
        cardPack = new CardPack();
    }

    public void getCard(){
        int playerSum = sumCards(-999999); //считаем сумму карт нужного игрока,вместо -999999 - id
        if (playerSum < 21) {
            List<Card> usedCards = null; //берем все использованные карты из базы
            try {
                Card card = cardPack.nextCard(usedCards);
                //добавляем эту карту в базу
            } catch (CardPack.AllCardsWereUsedException e) {
                e.printStackTrace();
            }
        }
    }


    private int sumCards(int playerId){
        List<Card> playerOrDialerCard = null; //берем карты игрока из базы

        int sumOfCards = 0;
        for (Card card : playerOrDialerCard){
            sumOfCards += card.getCardType().getValue();
        }

        return sumOfCards;
    }



}
