package com.portfolio.trading.data.repository.trading;

import com.portfolio.trading.data.entity.trading.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUpdatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);

    List<Transaction> findAllByUpdatedAtAfter(LocalDateTime today);
}
