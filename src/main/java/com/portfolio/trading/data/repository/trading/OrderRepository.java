package com.portfolio.trading.data.repository.trading;

import com.portfolio.trading.data.entity.trading.Order;
import com.portfolio.trading.data.entity.trading.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByTypeAndPriceLessThanEqualOrderByPrice(OrderType type, double price);
    List<Order> findByTypeAndPriceGreaterThanEqualOrderByPrice(OrderType type, double price);
}
