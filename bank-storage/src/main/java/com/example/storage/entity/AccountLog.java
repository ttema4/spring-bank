package com.example.storage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
public class AccountLog {
    @Id
    @GeneratedValue
    private Long id;

    private String accountId;
    private String eventType;
    private String eventJson;
    private LocalDateTime timestamp;
}
