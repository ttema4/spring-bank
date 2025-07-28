package com.example.storage.repository;

import com.example.storage.entity.AccountLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountLogRepository extends JpaRepository<AccountLog, Long> {}
