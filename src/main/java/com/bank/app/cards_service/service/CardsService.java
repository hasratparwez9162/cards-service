package com.bank.app.cards_service.service;

import com.bank.app.cards_service.entity.Card;
import com.bank.app.cards_service.exception.CardNotFoundException;

import java.util.List;

public interface CardsService {
    Card requestNewCard(Card card);

    Card activateCard(Long cardId);

    Card requestBlockCard(Long cardId) throws CardNotFoundException;

    Card requestUnblockCard(Long cardId) throws CardNotFoundException;

    Card blockCard(Long cardId);

    Card unblockCard(Long cardId);

    List<Card> getCardsByUserId(Long userId);

    Card cancelCard(Long cardId);

    List<Card> getAllNonActiveCards();

    Card getCardById(Long cardId);
}
