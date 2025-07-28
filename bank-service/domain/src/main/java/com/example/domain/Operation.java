package com.example.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Представляет операцию по банковскому счёту (пополнение, снятие, перевод).
 */
@Entity
@Table(name = "operations")
@Access(AccessType.FIELD)
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double amount;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Operation() {}

    /**
     * Создаёт новую операцию.
     *
     * @param description Описание операции (например, "Пополнение", "Снятие", "Перевод").
     * @param amount      Сумма операции (может быть отрицательной при снятии).
     * @throws IllegalArgumentException если `description` пустое.
     */
    public Operation(String description, double amount, Account account) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Описание операции не может быть пустым.");
        }

        this.description = description;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.account = account;
    }

    /**
     * Возвращает описание операции.
     *
     * @return Описание операции (например, "Пополнение", "Снятие", "Перевод").
     */
    public String getDescription() {
        return description;
    }

    /**
     * Возвращает сумму операции.
     *
     * @return Сумма операции (отрицательная при снятии).
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Возвращает дату и время выполнения операции.
     *
     * @return Дата и время операции.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Возвращает строковое представление операции.
     *
     * @return Строка с данными об операции.
     */
    @Override
    public String toString() {
        return "Operation{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                '}';
    }
}
