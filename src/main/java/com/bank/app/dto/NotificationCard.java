package com.bank.app.dto;

import com.bank.app.cards_service.entity.CardStatus;
import com.bank.app.cards_service.entity.CardType;
import lombok.Data;
@Data
public class NotificationCard {
        private String cardHolderName;
        private String cardNumber;
        private CardType cardType;
        private CardStatus status;
        private String message;

}
