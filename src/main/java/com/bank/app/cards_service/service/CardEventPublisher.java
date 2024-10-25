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

    /**
     * Send a message indicating that a card has been issued.
     * @param card The card that was issued.
     */
    public void sendIssueCardMessage(Card card) {
        logger.info("Sending issue card message for card number: {}", card.getCardNumber());
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card, cardNotification);
        template.send(TOPIC, "Card Issued", cardNotification);
        logger.info("Issue card message sent successfully for card number: {}", card.getCardNumber());
    }

    /**
     * Send a message indicating that a card has been blocked.
     * @param card The card that was blocked.
     */
    public void sendCardBlockMessage(Card card) {
        logger.info("Sending block card message for card number: {}", card.getCardNumber());
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card, cardNotification);
        cardNotification.setMessage("Card block Successfully");
        template.send(TOPIC, "Card Blocked", cardNotification);
        logger.info("Block card message sent successfully for card number: {}", card.getCardNumber());
    }

    /**
     * Send a message indicating that a card has been unblocked.
     * @param card The card that was unblocked.
     */
    public void sendCardUnblockMessage(Card card) {
        logger.info("Sending unblock card message for card number: {}", card.getCardNumber());
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card, cardNotification);
        cardNotification.setMessage("Card Unblock Successfully");
        template.send(TOPIC, "Card Unblocked", cardNotification);
        logger.info("Unblock card message sent successfully for card number: {}", card.getCardNumber());
    }

    /**
     * Send a message indicating that a card has expired.
     * @param card The card that expired.
     */
    public void sendCardExpireMessage(Card card) {
        logger.info("Sending expire card message for card number: {}", card.getCardNumber());
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card, cardNotification);
        cardNotification.setMessage("Card Expired");
        template.send(TOPIC, "Card Expired", cardNotification);
        logger.info("Expire card message sent successfully for card number: {}", card.getCardNumber());
    }

    /**
     * Send a message indicating that a card has been activated.
     * @param card The card that was activated.
     */
    public void sendCardActivateMessage(Card card) {
        logger.info("Sending activate card message for card number: {}", card.getCardNumber());
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card, cardNotification);
        cardNotification.setMessage("Card Activated");
        template.send(TOPIC, "Card Activated", cardNotification);
        logger.info("Activate card message sent successfully for card number: {}", card.getCardNumber());
    }
}