package com.bank.app.cards_service.controller;

import com.bank.app.cards_service.entity.Card;
import com.bank.app.cards_service.exception.CardNotFoundException;
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
public ResponseEntity<?> requestNewCard(@RequestBody Card cardRequest) {
    try {
        logger.debug("Requesting new card: {}", cardRequest);
        Card card = cardService.requestNewCard(cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(card);
    } catch (CardNotFoundException e) {
        logger.error("Card not found: {}", cardRequest);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found: " + cardRequest);
    } catch (Exception e) {
        logger.error("Error requesting new card: {}", cardRequest, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while requesting the new card. Please try again later.");
    }
}

    /**
     * Endpoint to activate a card.
     * @param cardId The ID of the card to be activated.
     * @return The activated card.
     */
   @Operation(summary = "Activate a card")
@PutMapping("/{cardId}/activate")
public ResponseEntity<?> activateCard(@PathVariable Long cardId) {
    try {
        logger.debug("Activating card with ID: {}", cardId);
        Card activatedCard = cardService.activateCard(cardId);
        return ResponseEntity.ok(activatedCard);
    } catch (CardNotFoundException e) {
        logger.error("Card not found with ID: {}", cardId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found with ID: " + cardId);
    } catch (Exception e) {
        logger.error("Error activating card with ID: {}", cardId, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while activating the card. Please try again later.");
    }
}
    /**
     * Controller method to handle user requests to block a card.
     * Sets the card status to PENDING_BLOCK for review.
     *
     * @param cardId The ID of the card to be blocked.
     * @return ResponseEntity containing the updated card or an error message.
     */
    @PutMapping("/{cardId}/request-block")
    public ResponseEntity<?> requestBlockCard(@PathVariable Long cardId) {
        try {
            logger.debug("Requesting block for card with ID: {}", cardId);

            // Calls the service layer to handle the block request.
            Card card = cardService.requestBlockCard(cardId);

            // Returns the updated card with PENDING_BLOCK status.
            return ResponseEntity.ok(card);
        } catch (CardNotFoundException e) {
            // Returns a 404 response if the card with specified ID does not exist.
            logger.error("Card not found with ID: {}", cardId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found with ID: " + cardId);
        } catch (Exception e) {
            // Logs and returns a 500 response if any other error occurs.
            logger.error("Error requesting block for card with ID: {}", cardId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while requesting the card block. Please try again later.");
        }
    }

    /**
     * Controller method to handle user requests to unblock a card.
     * Sets the card status to PENDING_UNBLOCK for review.
     *
     * @param cardId The ID of the card to be unblocked.
     * @return ResponseEntity containing the updated card or an error message.
     */
    @PutMapping("/{cardId}/request-unblock")
    public ResponseEntity<?> requestUnblockCard(@PathVariable Long cardId) {
        try {
            logger.debug("Requesting unblock for card with ID: {}", cardId);

            // Calls the service layer to handle the unblock request.
            Card card = cardService.requestUnblockCard(cardId);

            // Returns the updated card with PENDING_UNBLOCK status.
            return ResponseEntity.ok(card);
        } catch (CardNotFoundException e) {
            // Returns a 404 response if the card with specified ID does not exist.
            logger.error("Card not found with ID: {}", cardId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found with ID: " + cardId);
        } catch (Exception e) {
            // Logs and returns a 500 response if any other error occurs.
            logger.error("Error requesting unblock for card with ID: {}", cardId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while requesting the card unblock. Please try again later.");
        }
    }


    /**
     * Endpoint to block a card.
     * @param cardId The ID of the card to be blocked.
     * @return The blocked card.
     */
   @Operation(summary = "Block a card")
@PutMapping("/{cardId}/block")
public ResponseEntity<?> blockCard(@PathVariable Long cardId) {
    try {
        logger.debug("Blocking card with ID: {}", cardId);
        Card blockedCard = cardService.blockCard(cardId);
        return ResponseEntity.ok("Card is blocked Card Successfully");
    } catch (CardNotFoundException e) {
        logger.error("Card not found with ID: {}", cardId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found with ID: " + cardId);
    } catch (Exception e) {
        logger.error("Error blocking card with ID: {}", cardId, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while blocking the card. Please try again later.");
    }
}

    /**
     * Endpoint to unblock a card.
     * @param cardId The ID of the card to be unblocked.
     * @return The unblocked card.
     */
   @Operation(summary = "Unblock a card")
@PutMapping("/{cardId}/unblock")
public ResponseEntity<?> unblockCard(@PathVariable Long cardId) {
    try {
        logger.debug("Unblocking card with ID: {}", cardId);
        Card unblockedCard = cardService.unblockCard(cardId);
        return ResponseEntity.ok(unblockedCard);
    } catch (CardNotFoundException e) {
        logger.error("Card not found with ID: {}", cardId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found with ID: " + cardId);
    } catch (Exception e) {
        logger.error("Error unblocking card with ID: {}", cardId, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unblocking the card. Please try again later.");
    }
}

    /**
     * Endpoint to cancel a card.
     * @param cardId The ID of the card to be cancelled.
     * @return The cancelled card.
     */
  @Operation(summary = "Cancel a card")
@PutMapping("/{cardId}/cancel")
public ResponseEntity<?> cancelCard(@PathVariable Long cardId) {
    try {
        logger.debug("Cancelling card with ID: {}", cardId);
        Card cancelledCard = cardService.cancelCard(cardId);
        return ResponseEntity.ok(cancelledCard);
    } catch (CardNotFoundException e) {
        logger.error("Card not found with ID: {}", cardId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found with ID: " + cardId);
    } catch (Exception e) {
        logger.error("Error cancelling card with ID: {}", cardId, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while cancelling the card. Please try again later.");
    }
}

    /**
     * Endpoint to get card details by card ID.
     * @param cardId The ID of the card to retrieve.
     * @return The card details.
     */
    @Operation(summary = "Get card details")
@GetMapping("/{cardId}")
public ResponseEntity<?> getCardById(@PathVariable Long cardId) {
    try {
        logger.debug("Fetching card details for ID: {}", cardId);
        Card card = cardService.getCardById(cardId);
        return ResponseEntity.ok(card);
    } catch (CardNotFoundException e) {
        logger.error("Card not found with ID: {}", cardId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found with ID: " + cardId);
    } catch (Exception e) {
        logger.error("Error fetching card details for ID: {}", cardId, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the card details. Please try again later.");
    }
}

    /**
     * Endpoint to get all cards by user ID.
     * @param userId The ID of the user whose cards are to be retrieved.
     * @return A list of cards belonging to the user.
     */
   @Operation(summary = "Get all cards by user ID")
@GetMapping("/user/{userId}")
public ResponseEntity<?> getCardsByUserId(@PathVariable Long userId) {
    try {
        logger.debug("Fetching cards for user ID: {}", userId);
        List<Card> cards = cardService.getCardsByUserId(userId);
        return ResponseEntity.ok(cards);
    } catch (CardNotFoundException e) {
        logger.error("Cards not found for user ID: {}", userId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cards not found for user ID: " + userId);
    } catch (Exception e) {
        logger.error("Error fetching cards for user ID: {}", userId, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the cards. Please try again later.");
    }
}

    /**
     * Endpoint to get all cards except those with status ACTIVE.
     * @return A list of non-active cards.
     */
  @Operation(summary = "Get all cards except those with status ACTIVE")
@GetMapping("/non-active")
public ResponseEntity<?> getAllNonActiveCards() {
    try {
        logger.debug("Fetching all non-active cards");
        List<Card> cards = cardService.getAllNonActiveCards();
        return ResponseEntity.ok(cards);
    } catch (Exception e) {
        logger.error("Error fetching non-active cards", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the non-active cards. Please try again later.");
    }
}
}