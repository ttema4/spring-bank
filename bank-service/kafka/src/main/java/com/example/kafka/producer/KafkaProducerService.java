package com.example.kafka.producer;

import com.example.kafka.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserCreated(UserCreatedEvent event) {
        kafkaTemplate.send("user-created-topic", event.login(), event);
    }

    public void sendUserUpdated(UserUpdatedEvent event) {
        kafkaTemplate.send("user-updated-topic", event.login(), event);
    }

    public void sendAccountCreated(AccountCreatedEvent event) {
        kafkaTemplate.send("account-created-topic", event.id(), event);
    }

    public void sendAccountUpdated(AccountUpdatedEvent event) {
        kafkaTemplate.send("account-updated-topic", event.id(), event);
    }
}