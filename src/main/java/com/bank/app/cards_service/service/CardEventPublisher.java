package com.bank.app.cards_service.service;


import com.bank.app.cards_service.entity.Card;
import com.bank.core.entity.CardNotification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CardEventPublisher {
    private static final String TOPIC = "card-service-topic";
    @Autowired
    private KafkaTemplate<String,Object> template;

    public void sendIssueCardMessage(Card card) {
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card,cardNotification);
        template.send(TOPIC,"Card Issued",cardNotification);
    }
    public void sendCardBlockMessage(Card card) {
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card,cardNotification);
        cardNotification.setMessage("Card block Successfully");
        template.send(TOPIC,"Card Blocked",cardNotification);
    }
    public void sendCardUnblockMessage(Card card) {
        CardNotification cardNotification = new CardNotification();
        BeanUtils.copyProperties(card,cardNotification);
        cardNotification.setMessage("Card Unblock Successfully");
        template.send(TOPIC,"Card Unblocked",cardNotification);
    }
}
