package com.portfolio.trading.data.dto.trading;

import lombok.Getter;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
public class CandleDto {

    private final double startPrice;
    private final double endPrice;
    private final double highestPrice;
    private final double lowestPrice;
    private final Long date;
    private final double interval;

    public CandleDto(double startPrice, double endPrice, double highestPrice, double lowestPrice, Timestamp date, double interval) {
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.date = date.getTime();
        this.interval = interval;
    }
}
