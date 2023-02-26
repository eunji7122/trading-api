package com.portfolio.trading.data.dto.asset;

import lombok.Getter;

@Getter
public class MemberAssetDetailResponseDto {

    private final Long id;
    private final AssetResponseDto asset;
    private final double amount;
    private final double averagePurchasedPrice;
    private final double purchasedPrice;
    private final double evaluationPrice;
    private final double evaluationRate;

    public MemberAssetDetailResponseDto(MemberAssetResponseDto MemberAssetResponseDto, double purchasedPrice, double evaluationPrice, double evaluationRate) {
        this.id = MemberAssetResponseDto.getId();
        this.asset = MemberAssetResponseDto.getAsset();
        this.amount = MemberAssetResponseDto.getAmount();
        this.averagePurchasedPrice = MemberAssetResponseDto.getAveragePurchasedPrice();
        this.purchasedPrice = purchasedPrice;
        this.evaluationPrice = evaluationPrice;
        this.evaluationRate = evaluationRate;
    }
}
