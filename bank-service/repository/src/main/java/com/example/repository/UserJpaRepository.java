package com.example.repository;

import com.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA репозиторий для пользователей.
 */
@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
}
