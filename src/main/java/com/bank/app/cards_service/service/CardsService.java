package com.bank.app.cards_service.service;

import com.bank.app.cards_service.entity.Card;

import java.util.List;

public interface CardsService {
    Card issueCard(Card card);

    List<Card> getCardsByUserId(Long userId);

    void blockCard(Long cardId);

    void unblockCard(Long cardId);
    void deleteCard(Long cardId);
}
