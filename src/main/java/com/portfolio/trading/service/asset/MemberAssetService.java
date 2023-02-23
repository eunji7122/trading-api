package com.portfolio.trading.service.asset;

import com.portfolio.trading.data.dto.asset.AddMemberAssetRequestDto;
import com.portfolio.trading.data.dto.asset.MemberAssetResponseDto;
import com.portfolio.trading.data.dto.asset.MemberKrwAssetResponseDto;
import com.portfolio.trading.data.dto.asset.SubtractMemberAssetRequestDto;
import com.portfolio.trading.data.dto.trading.TradingPairResponseDto;
import com.portfolio.trading.data.entity.asset.Asset;
import com.portfolio.trading.data.entity.asset.MemberAsset;
import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.data.entity.trading.Order;
import com.portfolio.trading.data.repository.asset.MemberAssetRepository;
import com.portfolio.trading.data.repository.trading.TradingPairRepository;
import com.portfolio.trading.service.trading.TradingPairService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberAssetService {

    private final MemberAssetRepository memberAssetRepository;
    private final TradingPairRepository tradingPairRepository;

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
        return memberAssetRepository.findAllByMemberIdOrderById(memberId).stream().map(MemberAssetResponseDto::new).toList();
    }

    public MemberAssetResponseDto findAllByMemberIdAndAssetId(Long memberId, Long assetId) {
        return new MemberAssetResponseDto(memberAssetRepository.findAllByMemberIdAndAssetId(memberId, assetId).orElse(null));
    }

    public MemberKrwAssetResponseDto getMemberKrwAsset(Long memberId) {
        List<MemberAssetResponseDto> memberAssets = memberAssetRepository.findAllByMemberIdOrderById(memberId).stream().map(MemberAssetResponseDto::new).toList();
//        memberAssets.sort((MemberAssetResponseDto memberAsset1, MemberAssetResponseDto memberAsset2) -> memberAsset1.getId() <= memberAsset2.getId() ? -1 : 1);
        List<TradingPairResponseDto> tradingPairs = tradingPairRepository.findAll().stream().map(TradingPairResponseDto::new).toList();

        MemberAssetResponseDto krwAsset = memberAssets.stream().filter(memberAsset -> Objects.equals(memberAsset.getAsset().getSymbol(), "KRW")).findFirst().orElse(null);
        double krwAmount = krwAsset.getAmount(); // 보유 KRW
        double totalAmount = 0; // 총 보유 자산
        double totalPurchasedPrice = 0; // 총 매수 금액
        double totalEvaluationPrice = 0; // 총 평가 금액
        double totalEvaluationProfitAndLoss = 0; // 총 평가 손익
        double totalEvaluationRate = 0; // 총 평가 수익률

        for (int i = 0; i < memberAssets.size(); i++) {
            if (i >= 1) {
                // 총 매수 금액 계산
                double purchasedPrice = memberAssets.get(i).getAmount() * memberAssets.get(i).getAveragePurchasedPrice();
                totalPurchasedPrice += purchasedPrice;

                // 총 평가 금액 계산
                totalEvaluationPrice += purchasedPrice * (tradingPairs.get(i - 1).getLastPrice() / memberAssets.get(i).getAveragePurchasedPrice());
            }
        }

        totalAmount = totalEvaluationPrice + krwAsset.getAmount();
        totalEvaluationProfitAndLoss = totalEvaluationPrice - totalPurchasedPrice;
        totalEvaluationRate = (1 - (totalEvaluationPrice / totalPurchasedPrice)) * 100;

        return new MemberKrwAssetResponseDto(krwAmount, totalAmount, totalPurchasedPrice, totalEvaluationPrice, totalEvaluationProfitAndLoss, totalEvaluationRate);
    }

}
