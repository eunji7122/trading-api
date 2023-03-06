package com.portfolio.trading.data.dto.asset;

import lombok.Getter;

@Getter
public class MemberKrwAssetResponseDto {

    private final double krwAmount; // 보유 KRW
    private final double totalAmount; // 총 보유자산
    private final double totalPurchasedPrice; // 총 매수 금액
    private final double totalEvaluationPrice; // 총 평가 금액
    private final double totalEvaluationProfitAndLoss; // 총 평가 손익
    private final double totalEvaluationRate; // 총 평가 수익률

    public MemberKrwAssetResponseDto(double krwAmount, double totalAmount, double totalPurchasedPrice, double totalEvaluationPrice, double totalEvaluationProfitAndLoss, double totalEvaluationRate) {
        this.krwAmount = RoundValue(krwAmount);
        this.totalAmount = RoundValue(totalAmount);
        this.totalPurchasedPrice = RoundValue(totalPurchasedPrice);
        this.totalEvaluationPrice = RoundValue(totalEvaluationPrice);
        this.totalEvaluationProfitAndLoss = RoundValue(totalEvaluationProfitAndLoss);
        this.totalEvaluationRate = RoundValue(totalEvaluationRate);
    }

    private double RoundValue(double value) {
        return Math.round(value * 100000) / 100000.0;
    }
}
