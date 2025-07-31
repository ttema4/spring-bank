package com.example.service;

import com.example.domain.Account;
import com.example.kafka.producer.KafkaProducerService;
import com.example.repository.AccountJpaRepository;
import com.example.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountServiceImplTest {
    @Mock
    private KafkaProducerService kafkaProducerService;

    @Mock
    private AccountJpaRepository accountRepo;

    @Mock
    private UserJpaRepository userRepo;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void withdraw_success() {
        // GIVEN
        Account acc = new Account("acc1", "user1");
        acc.deposit(100.0);
        when(accountRepo.findById("acc1")).thenReturn(Optional.of(acc));

        // WHEN
        bankAccountService.withdraw("acc1", 50.0);

        // THEN
        verify(accountRepo).save(argThat(a -> a.getBalance() == 50.0));
    }

    @Test
    void withdraw_notEnoughFunds() {
        // GIVEN
        Account acc = new Account("acc1", "user1");
        acc.deposit(20.0);
        when(accountRepo.findById("acc1")).thenReturn(Optional.of(acc));

        // WHEN + THEN
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            bankAccountService.withdraw("acc1", 50.0);
        });
        assertEquals("Недостаточно средств на счёте.", ex.getMessage());
    }

    @Test
    void withdraw_accountNotFound() {
        // GIVEN
        when(accountRepo.findById("acc1")).thenReturn(Optional.empty());

        // WHEN + THEN
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            bankAccountService.withdraw("acc1", 50.0);
        });
        assertEquals("Счёт с ID acc1 не найден.", ex.getMessage());
    }

    @Test
    void deposit_ok() {
        // GIVEN
        Account acc = new Account("acc1", "user1");
        when(accountRepo.findById("acc1")).thenReturn(Optional.of(acc));

        // WHEN
        bankAccountService.deposit("acc1", 100.0);

        // THEN
        verify(accountRepo).save(argThat(a -> a.getBalance() == 100.0));
    }

    @Test
    void deposit_accountNotFound() {
        // GIVEN
        when(accountRepo.findById("acc1")).thenReturn(Optional.empty());

        // WHEN + THEN
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            bankAccountService.deposit("acc1", 100.0);
        });
        assertEquals("Счёт с ID acc1 не найден.", ex.getMessage());
    }
}
