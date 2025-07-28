package com.example.gateway.client;

import com.example.gateway.dto.bankresponse.UserInfo;
import com.example.gateway.dto.bankresponse.UserListResponse;
import com.example.gateway.dto.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankSystemUsersClientImpl implements BankSystemUsersClient {

    private final WebClient webClient;

    @Override
    public void createClientInBank(UserCreateRequest request) {
        webClient.post()
                .uri("/users")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public UserInfo getUser(String login) {
        return webClient.get()
                .uri("/users/{login}", login)
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();
    }

    @Override
    public UserListResponse getUsers(String gender, String hairColor) {
        List<UserInfo> users = webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/users");
                    if (gender != null) builder.queryParam("gender", gender);
                    if (hairColor != null) builder.queryParam("hairColor", hairColor);
                    return builder.build();
                })
                .retrieve()
                .bodyToFlux(UserInfo.class)
                .collectList()
                .block();

        return new UserListResponse(users);
    }

    @Override
    public void addFriend(String login, String friendLogin) {
        webClient.post()
                .uri("/users/{login}/friends/{friendLogin}", login, friendLogin)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public void removeFriend(String login, String friendLogin) {
        webClient.delete()
                .uri("/users/{login}/friends/{friendLogin}", login, friendLogin)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
