package com.example.repository;

import com.example.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA репозиторий для банковских счетов.
 */
@Repository
public interface AccountJpaRepository extends JpaRepository<Account, String> {
}
