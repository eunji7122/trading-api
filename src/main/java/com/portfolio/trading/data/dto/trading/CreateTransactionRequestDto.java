package com.portfolio.trading.data.dto.trading;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequestDto {

    private Long buyerId;
    private Long sellerId;
    private TradingPairResponseDto tradingPair;
    private double price;
    private double amount;

}
