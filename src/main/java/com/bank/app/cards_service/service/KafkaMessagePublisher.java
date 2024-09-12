package com.bank.app.cards_service.service;

import com.bank.app.dto.NotificationCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {
    @Autowired
    private KafkaTemplate<String,Object> template;

    public void issueCardNotification(NotificationCard card) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("card-service-topic", card);
            future.whenComplete((result, ex) -> {
                if (ex == null) {

                    System.out.println("Sent message=[" + card.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            card.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("ERROR : "+ ex.getMessage());
        }
    }
}
