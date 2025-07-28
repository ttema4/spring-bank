package com.example.service;

import com.example.domain.HairColor;
import com.example.domain.User;
import com.example.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.kafka.mapper.KafkaMapper;
import com.example.kafka.producer.KafkaProducerService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankUserServiceImpl implements BankUserService {

    private final UserJpaRepository userRepo;
    private final KafkaProducerService kafkaProducer;

    public BankUserServiceImpl(UserJpaRepository userRepo, KafkaProducerService kafkaProducer) {
        this.userRepo = userRepo;
        this.kafkaProducer = kafkaProducer;
    }
    @Override
    public User createUser(String login, String name, int age, String gender, String hairColor) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Логин не может быть пустым.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым.");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("Возраст должен быть больше 0.");
        }

        User user = new User(login, name, age, gender, HairColor.valueOf(hairColor.toUpperCase()));
        User savedUser = userRepo.save(user);

        kafkaProducer.sendUserCreated(KafkaMapper.toUserCreatedEvent(savedUser));

        return savedUser;
    }

    @Override
    public void addFriend(String userLogin, String friendLogin) {
        User user = userRepo.findById(userLogin)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден."));
        if (!user.getFriendsLogins().contains(friendLogin)) {
            user.addFriend(friendLogin);
            User updatedUser = userRepo.save(user);
            kafkaProducer.sendUserUpdated(KafkaMapper.toUserUpdatedEvent(updatedUser));
        }
    }

    @Override
    public void removeFriend(String userLogin, String friendLogin) {
        User user = userRepo.findById(userLogin)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден."));
        if (user.getFriendsLogins().contains(friendLogin)) {
            user.removeFriend(friendLogin);
            User updatedUser = userRepo.save(user);
            kafkaProducer.sendUserUpdated(KafkaMapper.toUserUpdatedEvent(updatedUser));
        }
    }

    @Override
    public Optional<User> getUser(String login) {
        return userRepo.findById(login);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
