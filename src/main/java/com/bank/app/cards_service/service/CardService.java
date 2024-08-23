package com.bank.app.cards_service.service;

import com.bank.app.cards_service.entity.Card;
import com.bank.app.cards_service.entity.CardStatus;
import com.bank.app.cards_service.repo.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class CardService {
    @Autowired
    private CardsRepository cardRepository;

    public Card issueCard(Card card) {
        // Logic for issuing a new card
        // Generate a card number
        String cardNumber = generateCardNumber();
        card.setCardNumber(cardNumber);

        // Set default values
        card.setExpiryDate(LocalDate.now().plusYears(10)); // Card valid for 3 years from now
        card.setAvailableLimit(card.getCreditLimit() != null ? card.getCreditLimit() : BigDecimal.ZERO); // Set available limit if not provided
        card.setCreditLimit(card.getCreditLimit() != null ? card.getCreditLimit() : BigDecimal.ZERO);
        card.setStatus(CardStatus.ACTIVE); // Set default status to ACTIVE

        // Save the card to the database
        return cardRepository.save(card);

    }

    public List<Card> getCardsByUserId(Long userId) {
        return cardRepository.findByUserId(userId);
    }

    public void blockCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElse(null);
        if (card != null) {
            card.setStatus(CardStatus.BLOCKED);
            cardRepository.save(card);
        }
    }

    public void unblockCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElse(null);
        if (card != null) {
            card.setStatus(CardStatus.ACTIVE);
            cardRepository.save(card);
        }
    }

    // Method to generate a random card number (mock implementation)
    private String generateCardNumber() {
        Random random = new Random();
        return String.format("4%015d", random.nextLong(1000000000000000L)); // Simple mock card number starting with 4 (Visa)
    }

}
