package com.bank.app.cards_service.service;

import com.bank.app.cards_service.entity.Card;

import java.util.List;

public interface CardsService {
    Card requestNewCard(Card card);

    Card activateCard(Long cardId);

    Card blockCard(Long cardId);

    Card unblockCard(Long cardId);

    List<Card> getCardsByUserId(Long userId);

    Card cancelCard(Long cardId);

    List<Card> getAllNonActiveCards();

    Card getCardById(Long cardId);
}
