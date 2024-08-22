package com.bank.app.cards_service.repo;

import com.bank.app.cards_service.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends JpaRepository<Cards,Long> {
}
