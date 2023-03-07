package com.portfolio.trading.data.dto.trading;

import lombok.Getter;

@Getter
public class OrderGatheringResponseDto {

    private final double price;
    private final double amount;

    public OrderGatheringResponseDto(double price, double amount) {
        this.price = price;
        this.amount = amount;
    }
}
