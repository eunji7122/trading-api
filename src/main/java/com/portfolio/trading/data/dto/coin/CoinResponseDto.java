package com.portfolio.trading.data.dto.coin;

import com.portfolio.trading.data.entity.coin.Coin;
import lombok.Getter;

@Getter
public class CoinResponseDto {

    private final Long id;

    private final String name;

    private final int lastPrice;

    public CoinResponseDto(Coin coin) {
        this.id = coin.getId();
        this.name = coin.getName();
        this.lastPrice = coin.getLastPrice();
    }
}
