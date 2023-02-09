package com.portfolio.trading.data.dto.asset;

import com.portfolio.trading.data.entity.asset.MemberAsset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAssetResponseDto {

    private final Long id;
    private final AssetResponseDto assetResponseDto;
    private final double amount;
    private final double averagePurchasedPrice;

    public MemberAssetResponseDto(MemberAsset memberAsset) {
        this.id = memberAsset.getId();
        this.assetResponseDto = new AssetResponseDto(memberAsset.getAsset());
        this.amount = memberAsset.getAmount();
        this.averagePurchasedPrice = memberAsset.getAveragePurchasedPrice();
    }
}
