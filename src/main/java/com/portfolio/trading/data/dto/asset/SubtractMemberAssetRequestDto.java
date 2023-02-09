package com.portfolio.trading.data.dto.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubtractMemberAssetRequestDto {

    private Long memberId;

    private Long assetId;

    private double amount;
}
