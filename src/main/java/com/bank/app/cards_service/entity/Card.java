package com.bank.app.cards_service.entity;

import com.bank.core.entity.CardStatus;
import com.bank.core.entity.CardType;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Card entity representing card details")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(example = "1234-5678-9876-5432")
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

