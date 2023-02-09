package com.portfolio.trading.data.repository.trading;

import com.portfolio.trading.data.entity.trading.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
