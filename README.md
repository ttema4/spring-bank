# 🏦 Spring Bank

**Spring Bank** — учебный микросервисный проект, моделирующий банковскую систему. Содержит работу с REST API, Spring Boot, Spring Security, Kafka, Docker и CI. Построен на основе серии лабораторных работ и расширен до продвинутой архитектуры с API Gateway, микросервисами и асинхронной коммуникацией.

## 🧱 Архитектура

Проект состоит из трёх независимых микросервисов:

| Модуль         | Назначение                                                                 |
|----------------|----------------------------------------------------------------------------|
| `bank-service` | Основная логика банковской системы: пользователи, счета, переводы, операции |
| `gateway`      | API Gateway. Авторизация, аутентификация, маршрутизация                   |
| `storage`      | Асинхронное хранение событий в Kafka (создание/изменение пользователей и счетов) |

Коммуникация:
- `gateway` ↔ `bank-service`: HTTP
- `bank-service` → `storage`: Kafka (топики `client-topic`, `account-topic`)

## 🚀 Возможности

### 🔐 API Gateway
- JWT-аутентификация и авторизация
- Разграничение прав: `Admin` / `Client`
- Перехват и перенаправление HTTP-запросов

### 👥 Пользователи и роли
- Администратор может создавать пользователей и админов
- Клиенты могут:
    - Управлять счётами
    - Добавлять/удалять друзей
    - Переводить средства, с учётом комиссии:
        - Между своими счётами — 0%
        - Друзьям — 3%
        - Остальным — 10%

### 📊 Storage
- Получает сообщения из Kafka
- Сохраняет историю событий (создание/изменение клиентов и счетов)

## 🧪 Технологии

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA + Hibernate
- PostgreSQL
- Kafka (или RabbitMQ)
- Docker + Docker Compose
- Swagger (OpenAPI)
- JUnit, Mockito
- CI/CD через GitHub Actions

## 🐳 Запуск через Docker

```bash
docker-compose up --build
```
📌 Убедитесь, что установлены:
- Docker
- Docker Compose

## 📮 Эндпоинты (частично)

### 🔐 Gateway

- `POST /login`
- `POST /admin/create-user`
- `GET /client/me`
- `GET /admin/users?gender=MALE&hairColor=BLACK`

### 💰 Bank-service

- `POST /accounts`
- `GET /accounts/{id}`
- `POST /accounts/{id}/deposit`
- `POST /accounts/{id}/withdraw`
- `POST /accounts/transfer`

## 📄 Swagger

Swagger доступен по адресу:
- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## ✅ CI/CD

В проекте настроен GitHub Actions:
- Сборка проекта
- Прогон тестов
- Генерация документации
