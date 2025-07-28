package com.example.storage.consumer;

import com.example.storage.dto.UserCreatedEvent;
import com.example.storage.dto.UserUpdatedEvent;
import com.example.storage.service.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final UserLogService logService;

    @KafkaListener(topics = "user-created-topic", containerFactory = "userCreatedListenerFactory")
    public void handleCreated(UserCreatedEvent event) {
        logService.log(event.login(), "CREATE", event);
    }

    @KafkaListener(topics = "user-updated-topic", containerFactory = "userUpdatedListenerFactory")
    public void handleUpdated(UserUpdatedEvent event) {
        logService.log(event.login(), "UPDATE", event);
    }
}