package com.bank.app.cards_service.repo;

import com.bank.app.cards_service.entity.Card;
import com.bank.core.entity.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends JpaRepository<Card, Long> {

    /**
     * Retrieve cards by user ID.
     * @param userId The ID of the user whose cards are to be retrieved.
     * @return A list of cards belonging to the user.
     */
    List<Card> findByUserId(Long userId);

    /**
     * Retrieve cards by status.
     * @param cardStatus The status of the cards to be retrieved.
     * @return A list of cards with the specified status.
     */
    List<Card> findByStatus(CardStatus cardStatus);

    /**
     * Retrieve cards that do not have the specified status.
     * @param status The status to exclude from the results.
     * @return A list of cards that do not have the specified status.
     */
    List<Card> findByStatusNot(CardStatus status);
}