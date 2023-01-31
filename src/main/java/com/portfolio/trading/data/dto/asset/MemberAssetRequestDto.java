package com.portfolio.trading.data.dto.asset;

import com.portfolio.trading.data.entity.asset.Asset;
import com.portfolio.trading.data.entity.asset.MemberAsset;
import com.portfolio.trading.data.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAssetRequestDto {

    private Member member;

    private Asset asset;

    private double amount;

    private double averagePurchasedPrice;

    public MemberAsset toEntity() {
        return MemberAsset.builder()
                .member(member)
                .asset(asset)
                .amount(amount)
                .averagePurchasedPrice(averagePurchasedPrice)
                .build();
    }

}
