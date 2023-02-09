package com.portfolio.trading.service.asset;

import com.portfolio.trading.data.dto.asset.AddMemberAssetRequestDto;
import com.portfolio.trading.data.dto.asset.MemberAssetResponseDto;
import com.portfolio.trading.data.dto.asset.SubtractMemberAssetRequestDto;
import com.portfolio.trading.data.entity.asset.Asset;
import com.portfolio.trading.data.entity.asset.MemberAsset;
import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.data.repository.asset.MemberAssetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberAssetService {

    private final MemberAssetRepository memberAssetRepository;

    public MemberAssetResponseDto addMemberAsset(AddMemberAssetRequestDto addMemberAssetRequestDto) {
        Optional<MemberAsset> optionalMemberAsset = memberAssetRepository.findAllByMemberIdAndAssetId(addMemberAssetRequestDto.getMemberId(), addMemberAssetRequestDto.getAssetId());
        if (optionalMemberAsset.isPresent()) {
            MemberAsset memberAsset = optionalMemberAsset.get();

            if (addMemberAssetRequestDto.isUpdateAveragePurchasedPrice()) {
                memberAsset.setAveragePurchasedPrice(
                        (memberAsset.getAmount() * memberAsset.getAveragePurchasedPrice() + addMemberAssetRequestDto.getAmount() * addMemberAssetRequestDto.getAveragePurchasedPrice())
                                / (memberAsset.getAmount() + addMemberAssetRequestDto.getAmount())
                );
            }

            memberAsset.setAmount(memberAsset.getAmount() + addMemberAssetRequestDto.getAmount());
            memberAssetRepository.save(memberAsset);
            return new MemberAssetResponseDto(memberAsset);
        } else {
            // add
            MemberAsset newMemberAsset = MemberAsset.builder()
                    .member(Member.builder()
                            .id(addMemberAssetRequestDto.getMemberId()).build())
                    .asset(Asset.builder().id(addMemberAssetRequestDto.getAssetId()).build())
                    .amount(addMemberAssetRequestDto.getAmount())
                    .averagePurchasedPrice(addMemberAssetRequestDto.isUpdateAveragePurchasedPrice()
                            ? addMemberAssetRequestDto.getAveragePurchasedPrice() : 0)
                    .build();
            memberAssetRepository.save(newMemberAsset);
            return new MemberAssetResponseDto(newMemberAsset);
        }
    }

    public MemberAssetResponseDto subtractMemberAsset(SubtractMemberAssetRequestDto subtractMemberAssetRequestDto) {
        Optional<MemberAsset> optionalMemberAsset = memberAssetRepository.findAllByMemberIdAndAssetId(subtractMemberAssetRequestDto.getMemberId(), subtractMemberAssetRequestDto.getAssetId());
        if (optionalMemberAsset.isEmpty()) {
            throw new RuntimeException();
        } else {
            MemberAsset memberAsset = optionalMemberAsset.get();
            double amount = memberAsset.getAmount() - subtractMemberAssetRequestDto.getAmount();
            if (amount < 0) {
                throw new RuntimeException();
            }
            memberAsset.setAmount(amount);
            memberAssetRepository.save(memberAsset);
            return new MemberAssetResponseDto(memberAsset);
        }
    }

    public List<MemberAssetResponseDto> findAllByMemberId(Long memberId) {
        return memberAssetRepository.findAllByMemberId(memberId).stream().map(MemberAssetResponseDto::new).toList();
    }

    public MemberAssetResponseDto findAllByMemberIdAndAssetId(Long memberId, Long assetId) {
        return new MemberAssetResponseDto(memberAssetRepository.findAllByMemberIdAndAssetId(memberId, assetId).orElse(null));
    }

}
