package com.portfolio.trading.data.repository.trading;

import com.portfolio.trading.data.entity.trading.TradingPair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingPairRepository extends JpaRepository<TradingPair, Long> {


}
