package com.portfolio.trading.data.dto.trading;

import com.portfolio.trading.data.entity.trading.OrderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {

    private Long tradingPairId;

    private OrderType type;

    private double price;

    private double amount;
}
