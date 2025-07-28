package com.example.service;

import com.example.domain.Account;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {

    Account createAccount(String accountId, String ownerLogin);

    void deposit(String accountId, double amount);

    void withdraw(String accountId, double amount);

    void transfer(String fromAccountId, String toAccountId, double amount);

    double getBalance(String accountId);

    Optional<Account> getAccount(String accountId);

    List<Account> getAllAccounts();
}
