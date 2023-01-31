package com.portfolio.trading.data.dto.asset;

import com.portfolio.trading.data.entity.asset.Asset;
import lombok.Getter;

@Getter
public class AssetResponseDto {

    private final Long id;

    private final String name;

    private final String symbol;

    public AssetResponseDto(Asset asset) {
        this.id = asset.getId();
        this.name = asset.getName();
        this.symbol = asset.getSymbol();
    }
}
