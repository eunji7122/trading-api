package com.portfolio.trading.data.repository.trading;

import com.portfolio.trading.data.entity.trading.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, CustomTransactionRepository {

    List<Transaction> findAllByUpdatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);

    List<Transaction> findAllByUpdatedAtAfter(LocalDateTime today);

    List<Transaction> findByTradingPairIdAndUpdatedAtAfterOrderByPrice(Long tradingPairId, LocalDateTime today);

    @Query(value = "SELECT DATE_TRUNC('day', T.created_at) as day, max(price) as highestPrice, min(price) lowestPrice FROM transaction T where trading_pair_id = :id AND T.created_at > :startTime AND T.created_at < :endTime GROUP BY day", nativeQuery = true)
    List<Map<String, Object>> findHighestAndLowestPricesByDay(@Param("id") Long tradingPairId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query(value = "WITH added_row_number AS ( SELECT id, price, DATE_TRUNC('day', created_at) as day, ROW_NUMBER() OVER(PARTITION BY DATE_TRUNC('day', created_at) ORDER BY created_at ASC) AS row_number FROM transaction WHERE trading_pair_id = :id AND created_at > :startTime AND created_at < :endTime) SELECT * FROM added_row_number WHERE row_number = 1", nativeQuery = true)
    List<Map<String, Object>> findStartPriceByDay(@Param("id") Long tradingPairId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query(value = "WITH added_row_number AS ( SELECT id, price, DATE_TRUNC('day', created_at) as day, ROW_NUMBER() OVER(PARTITION BY DATE_TRUNC('day', created_at) ORDER BY created_at DESC) AS row_number FROM transaction WHERE trading_pair_id = :id AND created_at > :startTime AND created_at < :endTime) SELECT * FROM added_row_number WHERE row_number = 1", nativeQuery = true)
    List<Map<String, Object>> findEndPriceByDay(@Param("id") Long tradingPairId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}
