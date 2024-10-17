package com.bank.app.cards_service.service;

import com.bank.app.cards_service.entity.Card;
import com.bank.core.entity.CardNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CardEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(CardEventPublisher.class);
    private static final String TOPIC = "card-service-topic";

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendIssueCardMessage(Card card) {
        logger.info("Sending issue card message for card number: {}", card.getCardNumber());
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card, cardNotification);
        template.send(TOPIC, "Card Issued", cardNotification);
        logger.info("Issue card message sent successfully for card number: {}", card.getCardNumber());
    }

    public void sendCardBlockMessage(Card card) {
        logger.info("Sending block card message for card number: {}", card.getCardNumber());
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card, cardNotification);
        cardNotification.setMessage("Card block Successfully");
        template.send(TOPIC, "Card Blocked", cardNotification);
        logger.info("Block card message sent successfully for card number: {}", card.getCardNumber());
    }

    public void sendCardUnblockMessage(Card card) {
        logger.info("Sending unblock card message for card number: {}", card.getCardNumber());
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card, cardNotification);
        cardNotification.setMessage("Card Unblock Successfully");
        template.send(TOPIC, "Card Unblocked", cardNotification);
        logger.info("Unblock card message sent successfully for card number: {}", card.getCardNumber());
    }
}