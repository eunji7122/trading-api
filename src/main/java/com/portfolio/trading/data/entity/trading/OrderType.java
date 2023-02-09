package com.portfolio.trading.data.entity.trading;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

public enum OrderType {
    BUY("BUY"),
    SELL("SELL");

    @Getter
    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static OrderType from(String value) {
        for (OrderType orderType : OrderType.values()) {
            if (orderType.getValue().equals(value)) return orderType;
        }
        return null;
    }
}
