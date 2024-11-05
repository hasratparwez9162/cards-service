package com.bank.app.cards_service.exception;

/**
 * Exception thrown when a card is not found.
 */
public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String message) {
        super(message);
    }
}
