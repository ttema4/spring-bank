package com.example.storage.service;

import com.example.storage.entity.AccountLog;
import com.example.storage.repository.AccountLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountLogService {

    private final AccountLogRepository accountLogRepo;
    private final ObjectMapper objectMapper;

    public void log(String accountId, String eventType, Object event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            AccountLog log = new AccountLog();
            log.setAccountId(accountId);
            log.setEventType(eventType);
            log.setEventJson(json);
            log.setTimestamp(LocalDateTime.now());

            accountLogRepo.save(log);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось сериализовать событие счёта", e);
        }
    }
}