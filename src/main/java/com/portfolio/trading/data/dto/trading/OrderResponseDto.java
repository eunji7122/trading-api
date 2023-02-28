package com.portfolio.trading.data.dto.trading;

import com.portfolio.trading.data.entity.trading.Order;
import com.portfolio.trading.data.entity.trading.OrderType;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class OrderResponseDto {

    private final long id;
    private final TradingPairResponseDto tradingPairResponseDto;
    private final OrderType orderType;
    private final double price;
    private final double amount;
    private final double filledAmount;
    private final String updatedAt;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.tradingPairResponseDto = new TradingPairResponseDto(order.getTradingPair());
        this.orderType = order.getType();
        this.price = order.getPrice();
        this.amount = order.getAmount();
        this.filledAmount = order.getFilledAmount();
        this.updatedAt = order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
    }

}
