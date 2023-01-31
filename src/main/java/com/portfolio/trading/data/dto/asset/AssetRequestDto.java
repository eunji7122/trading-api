package com.portfolio.trading.data.dto.asset;

import com.portfolio.trading.data.entity.asset.Asset;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssetRequestDto {

    private String name;

    private String symbol;

    public Asset toEntity() {
        return Asset.builder()
                .name(name)
                .symbol(symbol)
                .build();
    }
}
