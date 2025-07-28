package com.example.gateway.client;

import com.example.gateway.dto.bankresponse.AccountInfo;
import com.example.gateway.dto.bankresponse.AccountOperationsResponse;
import com.example.gateway.dto.bankresponse.OperationInfo;
import com.example.gateway.dto.bankresponse.UserAccountListResponse;
import com.example.gateway.dto.request.CreateAccountRequest;
import com.example.gateway.dto.request.TransferRequest;
import com.example.gateway.exception.AccessDeniedToAccountException;
import com.example.gateway.exception.AccountAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankSystemAccountsClientImpl implements BankSystemAccountsClient {

    private final WebClient webClient;

    @Override
    public UserAccountListResponse getUserAccounts(String login) {
        List<AccountInfo> accounts =  webClient.get()
                .uri("/accounts/user/{login}", login)
                .retrieve()
                .bodyToFlux(AccountInfo.class)
                .collectList()
                .block();
        return new UserAccountListResponse(accounts);
    }

    @Override
    public AccountInfo getAccountForClient(String login, String id) {
        AccountInfo account = webClient.get()
                .uri("/accounts/{id}", id)
                .retrieve()
                .bodyToMono(AccountInfo.class)
                .block();

        if (account == null || !account.ownerLogin().equals(login)) {
            throw new AccessDeniedToAccountException(id);
        }

        return account;
    }

    @Override
    public void createAccount(String login, String id) {
        try {
            webClient.get()
                    .uri("/accounts/{id}", id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            throw new AccountAlreadyExistsException(id);
        } catch (WebClientResponseException.NotFound ignored) {}

        webClient.post()
                .uri("/accounts")
                .bodyValue(new CreateAccountRequest(id, login))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public void deposit(String login, String id, double amount) {
        AccountInfo account = webClient.get()
                .uri("/accounts/{id}", id)
                .retrieve()
                .bodyToMono(AccountInfo.class)
                .block();

        if (account == null || !account.ownerLogin().equals(login)) {
            throw new AccessDeniedToAccountException(id);
        }

        webClient.post()
                .uri("/accounts/{id}/deposit?amount={amount}", id, amount)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public void withdraw(String login, String id, double amount) {
        AccountInfo account = webClient.get()
                .uri("/accounts/{id}", id)
                .retrieve()
                .bodyToMono(AccountInfo.class)
                .block();

        if (account == null || !account.ownerLogin().equals(login)) {
            throw new AccessDeniedToAccountException(id);
        }

        webClient.post()
                .uri("/accounts/{id}/withdraw?amount={amount}", id, amount)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public void transfer(String login, TransferRequest request) {
        AccountInfo account = webClient.get()
                .uri("/accounts/{id}", request.fromId())
                .retrieve()
                .bodyToMono(AccountInfo.class)
                .block();

        if (account == null || !account.ownerLogin().equals(login)) {
            throw new AccessDeniedToAccountException(request.fromId());
        }

        webClient.post()
                .uri("/accounts/transfer")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public UserAccountListResponse getAllAccounts() {
        List<AccountInfo> accounts =  webClient.get()
                .uri("/accounts")
                .retrieve()
                .bodyToFlux(AccountInfo.class)
                .collectList()
                .block();

        return new UserAccountListResponse(accounts);
    }

    @Override
    public AccountOperationsResponse getAccountOperations(String id, String type) {
        List<OperationInfo> operations = webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/accounts/{id}/operations");
                    if (type != null) builder.queryParam("type", type);
                    return builder.build(id);
                })
                .retrieve()
                .bodyToFlux(OperationInfo.class)
                .collectList()
                .block();

        return new AccountOperationsResponse(operations);
    }
}
