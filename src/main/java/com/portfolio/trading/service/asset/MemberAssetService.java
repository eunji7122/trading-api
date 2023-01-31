package com.portfolio.trading.service.asset;

import com.portfolio.trading.data.dto.asset.MemberAssetRequestDto;
import com.portfolio.trading.data.dto.asset.MemberAssetResponseDto;
import com.portfolio.trading.data.entity.asset.MemberAsset;
import com.portfolio.trading.data.repository.asset.MemberAssetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemberAssetService {

    private final MemberAssetRepository memberAssetRepository;

    public MemberAssetResponseDto saveMemberCoin(MemberAssetRequestDto memberAssetRequestDto) {
        MemberAsset savedMemberAsset = memberAssetRepository.save(memberAssetRequestDto.toEntity());
        return new MemberAssetResponseDto(savedMemberAsset);
    }

    public List<MemberAssetResponseDto> findAllByMemberId(Long memberId) {
        return memberAssetRepository.findAllByMemberId(memberId).stream().map(MemberAssetResponseDto::new).toList();
    }

    public MemberAssetResponseDto updateMemberCoinByBuying(Long id, MemberAssetRequestDto memberAssetRequestDto) {
        MemberAsset foundMemberAsset = memberAssetRepository.findById(id).orElse(null);
        foundMemberAsset.setAmount(CalculateAmountByBuying(foundMemberAsset, memberAssetRequestDto));
        foundMemberAsset.setAveragePurchasedPrice(CalculateAveragePurchasedPriceByBuying(foundMemberAsset, memberAssetRequestDto));
        MemberAsset updatedMemberAsset = memberAssetRepository.save(foundMemberAsset);

        return new MemberAssetResponseDto(updatedMemberAsset);
    }

    public MemberAssetResponseDto updateMemberCoinBySelling(Long id, MemberAssetRequestDto memberAssetRequestDto) {
        MemberAsset foundMemberAsset = memberAssetRepository.findById(id).orElse(null);
        foundMemberAsset.setAmount(CalculateAmountBySelling(foundMemberAsset, memberAssetRequestDto));
        MemberAsset updatedMemberAsset = memberAssetRepository.save(foundMemberAsset);

        return new MemberAssetResponseDto(updatedMemberAsset);
    }

    public Long deleteMemberCoin(Long id) {
        memberAssetRepository.deleteById(id);
        return id;
    }

    private double CalculateAmountByBuying(MemberAsset memberAsset, MemberAssetRequestDto memberAssetRequestDto) {
        // 매수 시, 총 수량 계산
        return memberAsset.getAmount() + memberAssetRequestDto.getAmount();
    }

    private double CalculateAmountBySelling(MemberAsset memberAsset, MemberAssetRequestDto memberAssetRequestDto) {
        // 매도 시, 총 수량 계산
        return memberAsset.getAmount() - memberAssetRequestDto.getAmount();
    }

    private double CalculateAveragePurchasedPriceByBuying(MemberAsset memberAsset, MemberAssetRequestDto memberAssetRequestDto) {
        // 매수 시, 매수평균가 계산
        return (memberAsset.getAmount() * memberAsset.getAveragePurchasedPrice() + memberAssetRequestDto.getAmount() * memberAssetRequestDto.getAveragePurchasedPrice())
                / (memberAsset.getAmount() + memberAssetRequestDto.getAmount());
    }

}
