package com.example.domain;

import jakarta.persistence.*;
import java.util.*;

/**
 * Класс, представляющий банковский счёт.
 */
@Entity
@Table(name = "accounts")
@Access(AccessType.FIELD)
public class Account {

    @Id
    private String id;

    private double balance;

    private String ownerLogin;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Operation> operations = new ArrayList<>();

    public Account() {}

    /**
     * Создаёт новый счёт.
     *
     * @param id         Уникальный идентификатор счёта (не может быть null или пустым).
     * @param ownerLogin Логин владельца счёта (не может быть null или пустым).
     * @throws IllegalArgumentException если `id` или `ownerLogin` пусты.
     */
    public Account(String id, String ownerLogin) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID счёта не может быть пустым.");
        }
        if (ownerLogin == null || ownerLogin.trim().isEmpty()) {
            throw new IllegalArgumentException("Логин владельца не может быть пустым.");
        }

        this.id = id;
        this.ownerLogin = ownerLogin;
        this.balance = 0.0; // Начальный баланс 0
    }

    /**
     * Пополняет счёт на указанную сумму.
     *
     * @param amount Сумма для пополнения (должна быть больше 0).
     * @throws IllegalArgumentException если сумма меньше или равна 0.
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма пополнения должна быть больше 0.");
        }
        this.balance += amount;
        operations.add(new Operation("Пополнение", amount, this));
    }

    /**
     * Снимает указанную сумму со счёта.
     *
     * @param amount Сумма для снятия (должна быть больше 0 и не превышать баланс).
     * @throws IllegalArgumentException если сумма меньше или равна 0 или превышает баланс.
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма снятия должна быть больше 0.");
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Недостаточно средств на счёте.");
        }
        this.balance -= amount;
        operations.add(new Operation("Снятие", -amount, this));
    }

    /**
     * Возвращает уникальный идентификатор счёта.
     *
     * @return ID счёта.
     */
    public String getId() {
        return id;
    }

    /**
     * Возвращает логин владельца счёта.
     *
     * @return Логин владельца.
     */
    public String getOwnerLogin() {
        return ownerLogin;
    }

    /**
     * Возвращает текущий баланс счёта.
     *
     * @return Баланс счёта.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Возвращает список всех операций по счёту.
     *
     * @return Невозможный для изменения список операций.
     */
    public List<Operation> getOperations() {
        return Collections.unmodifiableList(operations);
    }

    /**
     * Возвращает строковое представление счёта.
     *
     * @return Строка с данными о счёте.
     */
    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", ownerLogin='" + ownerLogin + '\'' +
                ", operations=" + operations +
                '}';
    }
}
