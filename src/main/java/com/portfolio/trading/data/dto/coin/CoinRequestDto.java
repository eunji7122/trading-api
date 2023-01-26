package com.portfolio.trading.data.dto.coin;

import com.portfolio.trading.data.entity.coin.Coin;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoinRequestDto {

    private String name;

    private int lastPrice;

    public Coin toEntity() {
        return Coin.builder()
                .name(name)
                .lastPrice(lastPrice)
                .build();
    }
}
