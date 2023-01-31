package com.portfolio.trading.data.dto.asset;

import com.portfolio.trading.data.entity.asset.Asset;
import com.portfolio.trading.data.entity.asset.MemberAsset;
import com.portfolio.trading.data.entity.member.Member;
import lombok.Getter;

@Getter
public class MemberAssetResponseDto {

    private final Long id;

    private final Member member;

    private final Asset asset;

    private final double amount;

    private final double averagePurchasedPrice;

    public MemberAssetResponseDto(MemberAsset memberAsset) {
        this.id = memberAsset.getId();
        this.member = memberAsset.getMember();
        this.asset = memberAsset.getAsset();
        this.amount = memberAsset.getAmount();
        this.averagePurchasedPrice = memberAsset.getAveragePurchasedPrice();
    }
}
