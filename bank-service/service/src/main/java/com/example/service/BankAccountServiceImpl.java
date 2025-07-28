package com.example.service;

import com.example.domain.Account;
import com.example.domain.User;
import com.example.kafka.mapper.KafkaMapper;
import com.example.kafka.producer.KafkaProducerService;
import com.example.repository.AccountJpaRepository;
import com.example.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final AccountJpaRepository accountRepo;
    private final UserJpaRepository userRepo;
    private final KafkaProducerService kafkaProducer;

    public BankAccountServiceImpl(AccountJpaRepository accountRepo, UserJpaRepository userRepo, KafkaProducerService kafkaProducer) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Account createAccount(String accountId, String ownerLogin) {
        Optional<User> owner = userRepo.findById(ownerLogin);
        if (owner.isEmpty()) {
            throw new IllegalArgumentException("Владелец с логином " + ownerLogin + " не найден.");
        }

        Account account = new Account(accountId, ownerLogin);
        Account savedAccount = accountRepo.save(account);
        kafkaProducer.sendAccountCreated(KafkaMapper.toAccountCreatedEvent(savedAccount));
        return savedAccount;
    }

    @Override
    public void deposit(String accountId, double amount) {
        Account acc = accountRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Счёт с ID " + accountId + " не найден."));
        acc.deposit(amount);
        accountRepo.save(acc);
        kafkaProducer.sendAccountUpdated(KafkaMapper.toAccountUpdatedEvent(acc));
    }

    @Override
    public void withdraw(String accountId, double amount) {
        Account acc = accountRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Счёт с ID " + accountId + " не найден."));
        if (acc.getBalance() < amount) {
            throw new IllegalArgumentException("Недостаточно средств на счёте.");
        }
        acc.withdraw(amount);
        accountRepo.save(acc);
        kafkaProducer.sendAccountUpdated(KafkaMapper.toAccountUpdatedEvent(acc));
    }

    @Override
    public void transfer(String fromAccountId, String toAccountId, double amount) {
        Account from = accountRepo.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Счёт отправителя " + fromAccountId + " не найден."));
        Account to = accountRepo.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Счёт получателя " + toAccountId + " не найден."));

        User fromUser = userRepo.findById(from.getOwnerLogin())
                .orElseThrow(() -> new IllegalArgumentException("Отправитель не найден."));
        User toUser = userRepo.findById(to.getOwnerLogin())
                .orElseThrow(() -> new IllegalArgumentException("Получатель не найден."));

        double commissionPercent = 10.0;
        if (fromUser.getLogin().equals(toUser.getLogin())) {
            commissionPercent = 0.0;
        } else if (fromUser.getFriendsLogins().contains(toUser.getLogin())) {
            commissionPercent = 3.0;
        }

        double commission = amount * commissionPercent / 100.0;
        double total = amount + commission;

        if (from.getBalance() < total) {
            throw new IllegalArgumentException("Недостаточно средств для перевода.");
        }

        from.withdraw(total);
        to.deposit(amount);

        accountRepo.save(from);
        accountRepo.save(to);

        kafkaProducer.sendAccountUpdated(KafkaMapper.toAccountUpdatedEvent(from));
        kafkaProducer.sendAccountUpdated(KafkaMapper.toAccountUpdatedEvent(to));
    }

    @Override
    public double getBalance(String accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Счёт не найден."))
                .getBalance();
    }

    @Override
    public Optional<Account> getAccount(String accountId) {
        return accountRepo.findById(accountId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }
}
