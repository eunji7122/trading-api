package com.portfolio.trading.data.repository.coin;

import com.portfolio.trading.data.entity.coin.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    Optional<Coin> findByName(String name);
}
