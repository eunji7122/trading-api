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
        this.amount = RoundValue(MemberAssetResponseDto.getAmount());
        this.averagePurchasedPrice = RoundValue(MemberAssetResponseDto.getAveragePurchasedPrice());
        this.purchasedPrice = RoundValue(purchasedPrice);
        this.evaluationPrice = RoundValue(evaluationPrice);
        this.evaluationRate = RoundValue(evaluationRate);
    }

    private double RoundValue(double value) {
        return Math.round(value * 100000) / 100000.0;
    }
}
