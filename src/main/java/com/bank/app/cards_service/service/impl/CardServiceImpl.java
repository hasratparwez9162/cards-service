package com.bank.app.cards_service.service.impl;

import com.bank.app.cards_service.entity.Card;
import com.bank.app.cards_service.exception.ResourceNotFoundException;
import com.bank.app.cards_service.repo.CardsRepository;
import com.bank.app.cards_service.service.CardEventPublisher;
import com.bank.app.cards_service.service.CardsService;
import com.bank.core.entity.CardStatus;
import com.bank.core.entity.CardType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Card issueCard(Card card) {
        try {
            logger.info("Issuing a new card for user: {}", card.getUserId());
            String cardNumber = generateCardNumber();
            card.setCardNumber(cardNumber);

            card.setExpiryDate(LocalDate.now().plusYears(10));

            if (card.getCardType() == CardType.CREDIT) {
                if (card.getCreditLimit() == null) {
                    card.setCreditLimit(new BigDecimal("25000.00"));
                }
                card.setAvailableLimit(card.getCreditLimit());
            } else if (card.getCardType() == CardType.DEBIT) {
                card.setCreditLimit(null);
                card.setAvailableLimit(BigDecimal.ZERO);
            } else {
                throw new IllegalArgumentException("Unsupported Card Type: " + card.getCardType());
            }
            card.setStatus(CardStatus.ACTIVE);

            Card newCard = cardRepository.save(card);
            cardEventPublisher.sendIssueCardMessage(newCard);

            logger.info("Card issued successfully with card number: {}", newCard.getCardNumber());
            return newCard;
        } catch (Exception ex) {
            logger.error("Error issuing card: ", ex);
            throw ex;
        }
    }

    @Override
    public List<Card> getCardsByUserId(Long userId) {
        try {
            logger.info("Fetching cards for user ID: {}", userId);
            List<Card> cards = cardRepository.findByUserId(userId);
            if (cards.isEmpty()) {
                throw new ResourceNotFoundException("No cards found for user ID: " + userId);
            }
            logger.info("Fetched {} cards for user ID: {}", cards.size(), userId);
            return cards;
        } catch (Exception ex) {
            logger.error("Error fetching cards: ", ex);
            throw ex;
        }
    }

    @Override
    public void blockCard(Long cardId) {
        try {
            logger.info("Blocking card with ID: {}", cardId);
            Card card = cardRepository.findById(cardId).orElseThrow(() -> new ResourceNotFoundException("Card not found with ID: " + cardId));
            card.setStatus(CardStatus.BLOCKED);
            Card blockedCard = cardRepository.save(card);
            logger.info("Card with ID: {} blocked successfully", cardId);
            cardEventPublisher.sendCardBlockMessage(blockedCard);
        } catch (Exception ex) {
            logger.error("Error blocking card: ", ex);
            throw ex;
        }
    }

    @Override
    public void unblockCard(Long cardId) {
        try {
            logger.info("Unblocking card with ID: {}", cardId);
            Card card = cardRepository.findById(cardId).orElseThrow(() -> new ResourceNotFoundException("Card not found with ID: " + cardId));
            card.setStatus(CardStatus.ACTIVE);
            Card unblockedCard = cardRepository.save(card);
            logger.info("Card with ID: {} unblocked successfully", cardId);
            cardEventPublisher.sendCardUnblockMessage(unblockedCard);
        } catch (Exception ex) {
            logger.error("Error unblocking card: ", ex);
            throw ex;
        }
    }

    @Override
    public void deleteCard(Long cardId) {
        try {
            logger.info("Deleting card with ID: {}", cardId);
            if (!cardRepository.existsById(cardId)) {
                throw new ResourceNotFoundException("Card not found with ID: " + cardId);
            }
            cardRepository.deleteById(cardId);
            logger.info("Card with ID: {} deleted successfully", cardId);
        } catch (Exception ex) {
            logger.error("Error deleting card: ", ex);
            throw ex;
        }
    }

    private String generateCardNumber() {
        Random random = new Random();
        return String.format("4%015d", random.nextLong(1000000000000000L));
    }
}