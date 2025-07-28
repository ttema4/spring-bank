package com.example.storage.consumer;

import com.example.storage.dto.AccountCreatedEvent;
import com.example.storage.dto.AccountUpdatedEvent;
import com.example.storage.service.AccountLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEventConsumer {

    private final AccountLogService logService;

    @KafkaListener(topics = "account-created-topic", containerFactory = "accountCreatedListenerFactory")
    public void handleCreated(AccountCreatedEvent event) {
        logService.log(event.id(), "CREATE", event);
    }

    @KafkaListener(topics = "account-updated-topic", containerFactory = "accountUpdatedListenerFactory")
    public void handleUpdated(AccountUpdatedEvent event) {
        logService.log(event.id(), "UPDATE", event);
    }
}