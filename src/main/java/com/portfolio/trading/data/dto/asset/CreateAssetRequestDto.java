package com.portfolio.trading.data.dto.asset;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAssetRequestDto {

    private String name;
    private String symbol;
}
