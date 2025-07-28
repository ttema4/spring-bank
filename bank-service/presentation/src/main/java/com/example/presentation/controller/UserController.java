package com.example.presentation.controller;

import com.example.presentation.dto.UserCreateRequest;
import com.example.presentation.dto.UserDto;
import com.example.service.BankUserService;
import com.example.presentation.mapper.MapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final BankUserService bankUserService;

    public UserController(BankUserService bankUserService) {
        this.bankUserService = bankUserService;
    }

    @Operation(summary = "Создать пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь создан"),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные")
    })
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateRequest request) {
        System.out.println("1");
        var user = bankUserService.createUser(
                request.login(),
                request.name(),
                request.age(),
                request.gender(),
                request.hairColor()
        );
        System.out.println("2");
        return ResponseEntity.ok(MapperUtil.toUserDto(user));
    }

    @Operation(summary = "Получить пользователя по логину")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{login}")
    public ResponseEntity<UserDto> getUser(@PathVariable("login") String login) {
        Optional<UserDto> userOpt = bankUserService.getUser(login)
                .map(MapperUtil::toUserDto);

        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получение всех пользователей с фильтрацией по полу и цвету волос")
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "hairColor", required = false) String hairColor
    ) {
        List<UserDto> users = bankUserService.getAllUsers().stream()
                .filter(u -> gender == null || u.getGender().equalsIgnoreCase(gender))
                .filter(u -> hairColor == null || u.getHairColor().name().equalsIgnoreCase(hairColor))
                .map(MapperUtil::toUserDto)
                .toList();

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить друзей пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список друзей возвращён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{login}/friends")
    public ResponseEntity<List<UserDto>> getFriends(@PathVariable("login") String login) {
        return bankUserService.getUser(login)
                .map(user -> {
                    List<UserDto> friends = user.getFriendsLogins().stream()
                            .map(bankUserService::getUser)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .map(MapperUtil::toUserDto)
                            .toList();
                    return ResponseEntity.ok(friends);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Добавить друга")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Друг добавлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка при добавлении друга")
    })
    @PostMapping("/{login}/friends/{friendLogin}")
    public ResponseEntity<String> addFriend(
            @PathVariable("login") String login,
            @PathVariable("friendLogin") String friendLogin) {
        bankUserService.addFriend(login, friendLogin);
        return ResponseEntity.ok("Друг добавлен");
    }

    @Operation(summary = "Удалить друга")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Друг удалён"),
            @ApiResponse(responseCode = "400", description = "Ошибка при удалении друга")
    })
    @DeleteMapping("/{login}/friends/{friendLogin}")
    public ResponseEntity<String> removeFriend(
            @PathVariable("login") String login,
            @PathVariable("friendLogin") String friendLogin) {
        bankUserService.removeFriend(login, friendLogin);
        return ResponseEntity.ok("Друг удалён");
    }
}
