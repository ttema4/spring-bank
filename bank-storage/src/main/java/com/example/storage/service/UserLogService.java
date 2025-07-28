package com.example.storage.service;

import com.example.storage.entity.UserLog;
import com.example.storage.repository.UserLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserLogService {

    private final UserLogRepository userLogRepo;
    private final ObjectMapper objectMapper;

    public void log(String userId, String eventType, Object event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            UserLog log = new UserLog();
            log.setUserId(userId);
            log.setEventType(eventType);
            log.setEventJson(json);
            log.setTimestamp(LocalDateTime.now());

            userLogRepo.save(log);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось сериализовать событие пользователя", e);
        }
    }
}