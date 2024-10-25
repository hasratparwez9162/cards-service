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

    /**
     * Endpoint to request a new card.
     * @param cardRequest The card details for the new card request.
     * @return The newly created card.
     */
    @Operation(summary = "Request a new card")
    @PostMapping("/request")
    public ResponseEntity<Card> requestNewCard(@RequestBody Card cardRequest) {
        logger.debug("Requesting new card: {}", cardRequest);
        Card card = cardService.requestNewCard(cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(card);
    }

    /**
     * Endpoint to activate a card.
     * @param cardId The ID of the card to be activated.
     * @return The activated card.
     */
    @Operation(summary = "Activate a card")
    @PutMapping("/{cardId}/activate")
    public ResponseEntity<Card> activateCard(@PathVariable Long cardId) {
        logger.debug("Activating card with ID: {}", cardId);
        Card activatedCard = cardService.activateCard(cardId);
        return ResponseEntity.ok(activatedCard);
    }

    /**
     * Endpoint to block a card.
     * @param cardId The ID of the card to be blocked.
     * @return The blocked card.
     */
    @Operation(summary = "Block a card")
    @PutMapping("/{cardId}/block")
    public ResponseEntity<Card> blockCard(@PathVariable Long cardId) {
        logger.debug("Blocking card with ID: {}", cardId);
        Card blockedCard = cardService.blockCard(cardId);
        return ResponseEntity.ok(blockedCard);
    }

    /**
     * Endpoint to unblock a card.
     * @param cardId The ID of the card to be unblocked.
     * @return The unblocked card.
     */
    @Operation(summary = "Unblock a card")
    @PutMapping("/{cardId}/unblock")
    public ResponseEntity<Card> unblockCard(@PathVariable Long cardId) {
        logger.debug("Unblocking card with ID: {}", cardId);
        Card unblockedCard = cardService.unblockCard(cardId);
        return ResponseEntity.ok(unblockedCard);
    }

    /**
     * Endpoint to cancel a card.
     * @param cardId The ID of the card to be cancelled.
     * @return The cancelled card.
     */
    @Operation(summary = "Cancel a card")
    @PutMapping("/{cardId}/cancel")
    public ResponseEntity<Card> cancelCard(@PathVariable Long cardId) {
        logger.debug("Cancelling card with ID: {}", cardId);
        Card cancelledCard = cardService.cancelCard(cardId);
        return ResponseEntity.ok(cancelledCard);
    }

    /**
     * Endpoint to get card details by card ID.
     * @param cardId The ID of the card to retrieve.
     * @return The card details.
     */
    @Operation(summary = "Get card details")
    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getCardById(@PathVariable Long cardId) {
        logger.debug("Fetching card details for ID: {}", cardId);
        Card card = cardService.getCardById(cardId);
        return ResponseEntity.ok(card);
    }

    /**
     * Endpoint to get all cards by user ID.
     * @param userId The ID of the user whose cards are to be retrieved.
     * @return A list of cards belonging to the user.
     */
    @Operation(summary = "Get all cards by user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Card>> getCardsByUserId(@PathVariable Long userId) {
        logger.debug("Fetching cards for user ID: {}", userId);
        List<Card> cards = cardService.getCardsByUserId(userId);
        return ResponseEntity.ok(cards);
    }

    /**
     * Endpoint to get all cards except those with status ACTIVE.
     * @return A list of non-active cards.
     */
    @Operation(summary = "Get all cards except those with status ACTIVE")
    @GetMapping("/non-active")
    public ResponseEntity<List<Card>> getAllNonActiveCards() {
        logger.debug("Fetching all non-active cards");
        List<Card> cards = cardService.getAllNonActiveCards();
        return ResponseEntity.ok(cards);
    }
}