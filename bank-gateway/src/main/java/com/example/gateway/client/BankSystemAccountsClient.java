package com.example.gateway.client;

import com.example.gateway.dto.bankresponse.*;
import com.example.gateway.dto.request.TransferRequest;

public interface BankSystemAccountsClient {
    UserAccountListResponse getUserAccounts(String login);
    AccountInfo getAccountForClient(String login, String id);
    void createAccount(String login, String id);

    void deposit(String login, String id, double amount);
    void withdraw(String login, String id, double amount);
    void transfer(String login, TransferRequest request);

    UserAccountListResponse getAllAccounts();
    AccountOperationsResponse getAccountOperations(String id, String type);
}
