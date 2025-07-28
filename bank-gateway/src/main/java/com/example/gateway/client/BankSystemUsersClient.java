package com.example.gateway.client;

import com.example.gateway.dto.bankresponse.UserInfo;
import com.example.gateway.dto.bankresponse.UserListResponse;
import com.example.gateway.dto.request.UserCreateRequest;

import java.util.List;

public interface BankSystemUsersClient {
    void createClientInBank(UserCreateRequest request);

    UserInfo getUser(String login);
    UserListResponse getUsers(String gender, String hairColor);

    void addFriend(String login, String friendLogin);
    void removeFriend(String login, String friendLogin);
}
