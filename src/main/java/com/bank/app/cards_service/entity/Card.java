package com.bank.app.cards_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    private String cardHolderName;

    @Enumerated(EnumType.STRING)
    private CardType cardType;  // Enum (e.g., DEBIT, CREDIT)
    @Column(nullable = true)
    private BigDecimal creditLimit;  // For credit cards

    private BigDecimal availableLimit;  // Remaining limit for the card

    private LocalDate expiryDate;

    private Long userId;  // Foreign key to the User entity

    @Enumerated(EnumType.STRING)
    private CardStatus status;  // Enum for ACTIVE, BLOCKED, etc.
}

