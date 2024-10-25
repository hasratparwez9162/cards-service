package com.bank.app.cards_service.service.impl;

import com.bank.app.cards_service.entity.Card;
import com.bank.app.cards_service.repo.CardsRepository;
import com.bank.app.cards_service.service.CardEventPublisher;
import com.bank.app.cards_service.service.CardsService;
import com.bank.core.entity.CardStatus;
import com.bank.core.entity.CardType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class CardServiceImpl implements CardsService {
    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Autowired
    private CardsRepository cardRepository;

    @Autowired
    private CardEventPublisher cardEventPublisher;

    /**
     * Request a new card.
     * @param card The card details for the new card request.
     * @return The newly created card.
     */
    @Override
    public Card requestNewCard(Card card) {
        logger.debug("Requesting new card: {}", card);
        card.setStatus(CardStatus.PENDING_ACTIVATION);
        card.setCardNumber(generateCardNumber());
        card.setExpiryDate(LocalDate.now().plusYears(10));

        if (card.getCardType() == CardType.CREDIT) {
            card.setCreditLimit(new BigDecimal("25000"));
            card.setAvailableLimit(card.getCreditLimit());
        }

        Card savedCard = cardRepository.save(card);
        cardEventPublisher.sendIssueCardMessage(savedCard);
        return savedCard;
    }

    /**
     * Activate a card.
     * @param cardId The ID of the card to be activated.
     * @return The activated card.
     */
    @Override
    public Card activateCard(Long cardId) {
        logger.debug("Activating card with ID: {}", cardId);
        Card card = getCardById(cardId);
        if (card.getStatus() == CardStatus.PENDING_ACTIVATION) {
            card.setStatus(CardStatus.ACTIVE);
            Card activatedCard = cardRepository.save(card);
            cardEventPublisher.sendCardActivateMessage(activatedCard);
            return activatedCard;
        } else {
            throw new IllegalStateException("Card is not in a pending activation state");
        }
    }

    /**
     * Block a card.
     * @param cardId The ID of the card to be blocked.
     * @return The blocked card.
     */
    @Override
    public Card blockCard(Long cardId) {
        logger.debug("Blocking card with ID: {}", cardId);
        Card card = getCardById(cardId);
        if (card.getStatus() == CardStatus.ACTIVE) {
            card.setStatus(CardStatus.BLOCKED);
            Card blockedCard = cardRepository.save(card);
            cardEventPublisher.sendCardBlockMessage(blockedCard);
            return blockedCard;
        } else {
            throw new IllegalStateException("Only active cards can be blocked");
        }
    }

    /**
     * Unblock a card.
     * @param cardId The ID of the card to be unblocked.
     * @return The unblocked card.
     */
    @Override
    public Card unblockCard(Long cardId) {
        logger.debug("Unblocking card with ID: {}", cardId);
        Card card = getCardById(cardId);
        if (card.getStatus() == CardStatus.BLOCKED) {
            card.setStatus(CardStatus.ACTIVE);
            Card unblockedCard = cardRepository.save(card);
            cardEventPublisher.sendCardUnblockMessage(unblockedCard);
            return unblockedCard;
        } else {
            throw new IllegalStateException("Only blocked cards can be unblocked");
        }
    }

    /**
     * Cancel a card.
     * @param cardId The ID of the card to be cancelled.
     * @return The cancelled card.
     */
    @Override
    public Card cancelCard(Long cardId) {
        logger.debug("Cancelling card with ID: {}", cardId);
        Card card = getCardById(cardId);
        if (card.getStatus() != CardStatus.CANCELLED) {
            card.setStatus(CardStatus.CANCELLED);
            Card cancelledCard = cardRepository.save(card);
            cardEventPublisher.sendCardBlockMessage(cancelledCard);
            return cancelledCard;
        } else {
            throw new IllegalStateException("Card is already cancelled");
        }
    }

    /**
     * Get all cards except those with status ACTIVE.
     * @return A list of non-active cards.
     */
    @Override
    public List<Card> getAllNonActiveCards() {
        logger.debug("Fetching all non-active cards");
        return cardRepository.findByStatusNot(CardStatus.ACTIVE);
    }

    /**
     * Scheduled task to automatically expire cards.
     */
    @Scheduled(fixedRate = 600000)
    public void expireCards() {
        logger.debug("Expiring cards");
        List<Card> activeCards = cardRepository.findByStatus(CardStatus.ACTIVE);
        for (Card card : activeCards) {
            if (card.getExpiryDate().isBefore(LocalDate.now())) {
                card.setStatus(CardStatus.EXPIRED);
                Card expiredCard = cardRepository.save(card);
                cardEventPublisher.sendCardExpireMessage(expiredCard);
            }
        }
    }

    /**
     * Get card by ID.
     * @param cardId The ID of the card to retrieve.
     * @return The card details.
     */
    @Override
    public Card getCardById(Long cardId) {
        logger.debug("Fetching card by ID: {}", cardId);
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalStateException("Card not found"));
    }

    /**
     * Get all cards by user ID.
     * @param userId The ID of the user whose cards are to be retrieved.
     * @return A list of cards belonging to the user.
     */
    @Override
    public List<Card> getCardsByUserId(Long userId) {
        logger.debug("Fetching cards by user ID: {}", userId);
        return cardRepository.findByUserId(userId);
    }

    /**
     * Helper function to generate a card number.
     * @return A randomly generated card number.
     */
    private String generateCardNumber() {
        Random random = new Random();
        return String.format("4%015d", random.nextLong(1000000000000000L));
    }
}