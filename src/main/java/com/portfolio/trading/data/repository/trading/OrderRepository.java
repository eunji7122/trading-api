package com.portfolio.trading.data.repository.trading;

import com.portfolio.trading.data.entity.trading.Order;
import com.portfolio.trading.data.entity.trading.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByTypeAndPriceLessThanEqualOrderByPrice(OrderType type, double price);
    List<Order> findByTypeAndPriceGreaterThanEqualOrderByPrice(OrderType type, double price);
    List<Order> findAllByMemberIdOrderByIdDesc(Long memberId);
    @Query(value = "SELECT price, sum(amount) FROM \"order\" WHERE type = :type AND trading_pair_id = :tradingPairId GROUP BY price ORDER BY price desc", nativeQuery = true)
    List<Map<String, Double>> findPriceAndSumOfAmountByPrice(@Param("type") String type, @Param("tradingPairId") Long tradingPairId);
}
