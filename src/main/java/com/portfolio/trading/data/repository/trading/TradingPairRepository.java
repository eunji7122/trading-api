package com.portfolio.trading.data.repository.trading;

import com.portfolio.trading.data.entity.trading.TradingPair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradingPairRepository extends JpaRepository<TradingPair, Long> {

    List<TradingPair> findAllByOrderById();
}
