package com.bank.app.cards_service.service;
import com.bank.app.cards_service.entity.Card;

import com.bank.app.cards_service.repo.CardsRepository;

import com.bank.app.cards_service.service.impl.CardServiceImpl;
import com.bank.core.entity.CardStatus;
import com.bank.core.entity.CardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardServiceImplTest {

    @Mock
    private CardsRepository cardRepository;

    @Mock
    private CardEventPublisher cardEventPublisher;

    @InjectMocks
    private CardServiceImpl cardService;

    @BeforeEach
    void setUp() {
        System.out.println("CardServiceImplTest.setUp");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRequestNewCard() {
        Card card = new Card();
        card.setUserId(1L);
        card.setCardType(CardType.CREDIT);

        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.requestNewCard(card);

        assertNotNull(result);
        assertEquals(CardStatus.PENDING_ACTIVATION, result.getStatus());
        verify(cardRepository, times(1)).save(any(Card.class));
        verify(cardEventPublisher, times(1)).sendIssueCardMessage(any(Card.class));
    }

    @Test
    void testActivateCard() {
        Card card = new Card();
        card.setId(1L);
        card.setStatus(CardStatus.PENDING_ACTIVATION);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.activateCard(1L);

        assertNotNull(result);
        assertEquals(CardStatus.ACTIVE, result.getStatus());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).save(any(Card.class));
        verify(cardEventPublisher, times(1)).sendCardActivateMessage(any(Card.class));
    }

    @Test
    void testActivateCardThrowsException() {
        Card card = new Card();
        card.setId(1L);
        card.setStatus(CardStatus.ACTIVE);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            cardService.activateCard(1L);
        });

        assertEquals("Card is not in a pending activation state", exception.getMessage());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, never()).save(any(Card.class));
        verify(cardEventPublisher, never()).sendCardActivateMessage(any(Card.class));
    }

    @Test
    void testRequestBlockCard() {
        Card card = new Card();
        card.setId(1L);
        card.setStatus(CardStatus.ACTIVE);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.requestBlockCard(1L);

        assertNotNull(result);
        assertEquals(CardStatus.PENDING_BLOCK, result.getStatus());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void testRequestBlockCardThrowsException() {
        Card card = new Card();
        card.setId(1L);
        card.setStatus(CardStatus.BLOCKED);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            cardService.requestBlockCard(1L);
        });

        assertEquals("Card is not eligible for blocking in its current status.", exception.getMessage());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void testRequestUnblockCard() {
        Card card = new Card();
        card.setId(1L);
        card.setStatus(CardStatus.BLOCKED);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.requestUnblockCard(1L);

        assertNotNull(result);
        assertEquals(CardStatus.PENDING_UNBLOCK, result.getStatus());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void testRequestUnblockCardThrowsException() {
        Card card = new Card();
        card.setId(1L);
        card.setStatus(CardStatus.ACTIVE);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            cardService.requestUnblockCard(1L);
        });

        assertEquals("Card is not eligible for unblocking in its current status.", exception.getMessage());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void testBlockCard() {
        Card card = new Card();
        card.setId(1L);
        card.setStatus(CardStatus.ACTIVE);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.blockCard(1L);

        assertNotNull(result);
        assertEquals(CardStatus.BLOCKED, result.getStatus());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).save(any(Card.class));
        verify(cardEventPublisher, times(1)).sendCardBlockMessage(any(Card.class));
    }

    @Test
    void testUnblockCard() {
        Card card = new Card();
        card.setId(1L);
        card.setStatus(CardStatus.BLOCKED);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.unblockCard(1L);

        assertNotNull(result);
        assertEquals(CardStatus.ACTIVE, result.getStatus());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).save(any(Card.class));
        verify(cardEventPublisher, times(1)).sendCardUnblockMessage(any(Card.class));
    }

    @Test
    void testCancelCard() {
        Card card = new Card();
        card.setId(1L);
        card.setStatus(CardStatus.ACTIVE);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.cancelCard(1L);

        assertNotNull(result);
        assertEquals(CardStatus.CANCELLED, result.getStatus());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).save(any(Card.class));
        verify(cardEventPublisher, times(1)).sendCardBlockMessage(any(Card.class));
    }

    @Test
    void testGetCardById() {
        Card card = new Card();
        card.setId(1L);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        Card result = cardService.getCardById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(cardRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCardByIdThrowsException() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            cardService.getCardById(1L);
        });

        assertEquals("Card not found", exception.getMessage());
        verify(cardRepository, times(1)).findById(1L);
    }
}

