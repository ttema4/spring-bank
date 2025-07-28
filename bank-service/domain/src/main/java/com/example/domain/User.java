package com.example.domain;

import jakarta.persistence.*;
import java.util.*;

/**
 * Представляет пользователя банка.
 */
@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
public class User {

    @Id
    private String login;

    private String name;

    private int age;

    private String gender;

    @Enumerated(EnumType.STRING)
    private HairColor hairColor;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_login"))
    @Column(name = "friend_login")
    private List<String> friendsLogins = new ArrayList<>();

    public User() {}

    /**
     * Создаёт нового пользователя.
     *
     * @param login     Уникальный логин пользователя (не может быть null или пустым).
     * @param name      Имя пользователя (не может быть null или пустым).
     * @param age       Возраст пользователя (должен быть > 0).
     * @param gender    Пол пользователя (например, "M" или "F").
     * @param hairColor Цвет волос пользователя.
     * @throws IllegalArgumentException если переданы некорректные данные.
     */
    public User(String login, String name, int age, String gender, HairColor hairColor) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Логин не может быть пустым.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым.");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("Возраст должен быть больше 0.");
        }
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Пол не может быть пустым.");
        }
        if (hairColor == null) {
            throw new IllegalArgumentException("Цвет волос не может быть null.");
        }

        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
    }

    /**
     * Возвращает логин пользователя.
     *
     * @return Логин пользователя.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return Имя пользователя.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает возраст пользователя.
     *
     * @return Возраст пользователя.
     */
    public int getAge() {
        return age;
    }

    /**
     * Возвращает пол пользователя.
     *
     * @return Пол пользователя.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Возвращает цвет волос пользователя.
     *
     * @return Цвет волос пользователя.
     */
    public HairColor getHairColor() {
        return hairColor;
    }

    /**
     * Возвращает список логинов друзей пользователя.
     *
     * @return Невозможный для изменения список друзей.
     */
    public List<String> getFriendsLogins() {
        return Collections.unmodifiableList(friendsLogins);
    }

    /**
     * Добавляет друга в список друзей пользователя.
     *
     * @param friendLogin Логин друга (не может быть null или пустым).
     * @throws IllegalArgumentException если логин друга некорректный.
     */
    public void addFriend(String friendLogin) {
        if (friendLogin == null || friendLogin.trim().isEmpty()) {
            throw new IllegalArgumentException("Логин друга не может быть пустым.");
        }
        if (friendsLogins.contains(friendLogin)) {
            throw new IllegalArgumentException("Вы уже друзья.");
        }
        friendsLogins.add(friendLogin);
    }

    /**
     * Удаляет друга из списка друзей пользователя.
     *
     * @param friendLogin Логин друга (не может быть null или пустым).
     * @throws IllegalArgumentException если логин друга некорректный.
     */
    public void removeFriend(String friendLogin) {
        if (friendLogin == null || friendLogin.trim().isEmpty()) {
            throw new IllegalArgumentException("Логин друга не может быть пустым.");
        }
        if (!friendsLogins.contains(friendLogin)) {
            throw new IllegalArgumentException("Вы не друзья.");
        }
        friendsLogins.remove(friendLogin);
    }

    /**
     * Возвращает строковое представление пользователя.
     *
     * @return Строка с информацией о пользователе.
     */
    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", hairColor=" + hairColor +
                ", friends=" + friendsLogins +
                '}';
    }
}
