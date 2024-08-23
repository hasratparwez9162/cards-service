package com.bank.app.cards_service.repo;

import com.bank.app.cards_service.entity.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends JpaRepository<Card,Long> {
    List<Card> findByUserId(Long userId);  // Retrieve cards by user ID
}
