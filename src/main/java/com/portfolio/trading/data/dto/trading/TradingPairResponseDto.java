package com.portfolio.trading.data.dto.trading;

import com.portfolio.trading.data.dto.asset.AssetResponseDto;
import com.portfolio.trading.data.entity.trading.TradingPair;
import lombok.Getter;

@Getter
public class TradingPairResponseDto {

    private final long id;
    private final AssetResponseDto baseAsset;
    private final AssetResponseDto quoteAsset;
    private final double lastPrice;
    private final double changeRate;
    private final double tradingValue;

    public TradingPairResponseDto(TradingPair tradingPair) {
        this.id = tradingPair.getId();
        this.baseAsset = new AssetResponseDto(tradingPair.getBaseAsset());
        this.quoteAsset = new AssetResponseDto(tradingPair.getQuoteAsset());
        this.lastPrice = tradingPair.getLastPrice();
        this.changeRate = tradingPair.getChangeRate();
        this.tradingValue = tradingPair.getTradingValue();
    }
}
