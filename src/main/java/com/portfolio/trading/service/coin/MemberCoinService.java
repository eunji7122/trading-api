package com.portfolio.trading.service.coin;

import com.portfolio.trading.data.dto.coin.MemberCoinRequestDto;
import com.portfolio.trading.data.dto.coin.MemberCoinResponseDto;
import com.portfolio.trading.data.entity.coin.MemberCoin;
import com.portfolio.trading.data.repository.coin.MemberCoinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemberCoinService {

    private final MemberCoinRepository memberCoinRepository;

    public MemberCoinResponseDto saveMemberCoin(MemberCoinRequestDto memberCoinRequestDto) {
        MemberCoin savedMemberCoin = memberCoinRepository.save(memberCoinRequestDto.toEntity());
        return new MemberCoinResponseDto(savedMemberCoin);
    }

    public List<MemberCoinResponseDto> findAllByMemberId(Long memberId) {
        return memberCoinRepository.findAllByMemberId(memberId).stream().map(MemberCoinResponseDto::new).toList();
    }

    public MemberCoinResponseDto updateMemberCoinByBuying(Long id, MemberCoinRequestDto memberCoinRequestDto) {
        MemberCoin foundMemberCoin = memberCoinRepository.findById(id).orElse(null);
        foundMemberCoin.setAmount(CalculateAmountByBuying(foundMemberCoin, memberCoinRequestDto));
        foundMemberCoin.setAveragePurchasedPrice(CalculateAveragePurchasedPriceByBuying(foundMemberCoin, memberCoinRequestDto));
        MemberCoin updatedMemberCoin = memberCoinRepository.save(foundMemberCoin);

        return new MemberCoinResponseDto(updatedMemberCoin);
    }

    public MemberCoinResponseDto updateMemberCoinBySelling(Long id, MemberCoinRequestDto memberCoinRequestDto) {
        MemberCoin foundMemberCoin = memberCoinRepository.findById(id).orElse(null);
        foundMemberCoin.setAmount(CalculateAmountBySelling(foundMemberCoin, memberCoinRequestDto));
        MemberCoin updatedMemberCoin = memberCoinRepository.save(foundMemberCoin);

        return new MemberCoinResponseDto(updatedMemberCoin);
    }

    public Long deleteMemberCoin(Long id) {
        memberCoinRepository.deleteById(id);
        return id;
    }

    private int CalculateAmountByBuying(MemberCoin memberCoin, MemberCoinRequestDto memberCoinRequestDto) {
        // 매수 시, 총 수량 계산
        return memberCoin.getAmount() + memberCoinRequestDto.getAmount();
    }

    private int CalculateAmountBySelling(MemberCoin memberCoin, MemberCoinRequestDto memberCoinRequestDto) {
        // 매도 시, 총 수량 계산
        return memberCoin.getAmount() - memberCoinRequestDto.getAmount();
    }

    private int CalculateAveragePurchasedPriceByBuying(MemberCoin memberCoin, MemberCoinRequestDto memberCoinRequestDto) {
        // 매수 시, 매수평균가 계산
        return (memberCoin.getAmount() * memberCoin.getAveragePurchasedPrice() + memberCoinRequestDto.getAmount() * memberCoinRequestDto.getAveragePurchasedPrice())
                / (memberCoin.getAmount() + memberCoinRequestDto.getAmount());
    }

}
