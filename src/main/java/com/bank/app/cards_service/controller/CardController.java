package com.bank.app.cards_service.controller;


import com.bank.app.cards_service.entity.Card;
import com.bank.app.cards_service.service.impl.CardServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/cards")
@Tag(name = "Cards Service", description = "API for issuing and managing credit/debit cards")
public class CardController {

   private CardServiceImpl cardService;
    public CardController (CardServiceImpl cardService){
        this.cardService=cardService;
    }


//  Issue Cards like credit and Debit Cards
    @Operation(summary = "Issue a new card", description = "Issues a credit or debit card for a user")
    @PostMapping("/issue")
    public ResponseEntity<Card> issueCard( @RequestBody Card card) {
        Card newCard = cardService.issueCard(card);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }
//  Get cards details of a user
    @Operation(summary = "Get user's cards", description = "Fetch all cards associated with a specific user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Card>> getCardsByUserId(@PathVariable Long userId) {
        List<Card> cards = cardService.getCardsByUserId(userId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

//    block Card by cardId
    @Operation(summary = "Block a card", description = "Block a card by card ID")
    @PutMapping("/block/{cardId}")
    public ResponseEntity<String> blockCard(@PathVariable Long cardId) {
        cardService.blockCard(cardId);
        return new ResponseEntity<>("Card blocked successfully", HttpStatus.OK);
    }
//    Unblock Cards
    @Operation(summary = "Unblock a card", description = "Unblock a card by card ID")
    @PutMapping("/unblock/{cardId}")
    public ResponseEntity<String> unblockCard(@PathVariable Long cardId) {
        cardService.unblockCard(cardId);
        return new ResponseEntity<>("Card unblocked successfully", HttpStatus.OK);
    }
    @Operation(summary = "Delete a card", description = "Delete a card by card ID")
    @DeleteMapping("/delete/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return new ResponseEntity<>("Card Deleted successfully", HttpStatus.OK);
    }
}
