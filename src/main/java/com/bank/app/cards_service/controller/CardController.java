package com.bank.app.cards_service.controller;

import com.bank.app.cards_service.entity.Card;
import com.bank.app.cards_service.service.impl.CardServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@Tag(name = "Cards Service", description = "API for issuing and managing credit/debit cards")
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    private final CardServiceImpl cardService;

    public CardController(CardServiceImpl cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "Issue a new card", description = "Issues a credit or debit card for a user")
    @PostMapping("/issue")
    public ResponseEntity<Card> issueCard(@RequestBody Card card) {
        try {
            logger.info("Issuing a new card for user: {}", card.getUserId());
            Card newCard = cardService.issueCard(card);
            logger.info("Card issued successfully with card number: {}", newCard.getCardNumber());
            return new ResponseEntity<>(newCard, HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error("Error issuing card: ", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get user's cards", description = "Fetch all cards associated with a specific user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Card>> getCardsByUserId(@PathVariable Long userId) {
        try {
            logger.info("Fetching cards for user ID: {}", userId);
            List<Card> cards = cardService.getCardsByUserId(userId);
            logger.info("Fetched {} cards for user ID: {}", cards.size(), userId);
            return new ResponseEntity<>(cards, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error fetching cards: ", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Block a card", description = "Block a card by card ID")
    @PutMapping("/block/{cardId}")
    public ResponseEntity<String> blockCard(@PathVariable Long cardId) {
        try {
            logger.info("Blocking card with ID: {}", cardId);
            cardService.blockCard(cardId);
            logger.info("Card with ID: {} blocked successfully", cardId);
            return new ResponseEntity<>("Card blocked successfully", HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error blocking card: ", ex);
            return new ResponseEntity<>("Error blocking card", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Unblock a card", description = "Unblock a card by card ID")
    @PutMapping("/unblock/{cardId}")
    public ResponseEntity<String> unblockCard(@PathVariable Long cardId) {
        try {
            logger.info("Unblocking card with ID: {}", cardId);
            cardService.unblockCard(cardId);
            logger.info("Card with ID: {} unblocked successfully", cardId);
            return new ResponseEntity<>("Card unblocked successfully", HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error unblocking card: ", ex);
            return new ResponseEntity<>("Error unblocking card", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a card", description = "Delete a card by card ID")
    @DeleteMapping("/delete/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId) {
        try {
            logger.info("Deleting card with ID: {}", cardId);
            cardService.deleteCard(cardId);
            logger.info("Card with ID: {} deleted successfully", cardId);
            return new ResponseEntity<>("Card deleted successfully", HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error deleting card: ", ex);
            return new ResponseEntity<>("Error deleting card", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}