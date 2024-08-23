package com.bank.app.cards_service.controller;

import com.bank.app.cards_service.entity.Card;
import com.bank.app.cards_service.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/issue")
    public ResponseEntity<Card> issueCard(@RequestBody Card card) {
        Card newCard = cardService.issueCard(card);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Card>> getCardsByUserId(@PathVariable Long userId) {
        List<Card> cards = cardService.getCardsByUserId(userId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PutMapping("/block/{cardId}")
    public ResponseEntity<String> blockCard(@PathVariable Long cardId) {
        cardService.blockCard(cardId);
        return new ResponseEntity<>("Card blocked successfully", HttpStatus.OK);
    }
    @PutMapping("/unblock/{cardId}")
    public ResponseEntity<String> unblockCard(@PathVariable Long cardId) {
        cardService.unblockCard(cardId);
        return new ResponseEntity<>("Card unblocked successfully", HttpStatus.OK);
    }
}
