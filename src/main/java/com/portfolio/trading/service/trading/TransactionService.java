package com.portfolio.trading.service.trading;

import com.portfolio.trading.data.dto.asset.AddMemberAssetRequestDto;
import com.portfolio.trading.data.dto.asset.SubtractMemberAssetRequestDto;
import com.portfolio.trading.data.dto.trading.CreateTransactionRequestDto;
import com.portfolio.trading.data.dto.trading.TransactionResponseDto;
import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.data.entity.trading.TradingPair;
import com.portfolio.trading.data.entity.trading.Transaction;
import com.portfolio.trading.data.repository.trading.TransactionRepository;
import com.portfolio.trading.service.asset.MemberAssetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MemberAssetService memberAssetService;

    public TransactionResponseDto createTransaction(CreateTransactionRequestDto createTransactionRequestDto) {

        memberAssetService.addMemberAsset(
                new AddMemberAssetRequestDto(
                        createTransactionRequestDto.getBuyerId(),
                        createTransactionRequestDto.getTradingPair().getBaseAsset().getId(),
                        createTransactionRequestDto.getAmount(),
                        createTransactionRequestDto.getPrice(),
                        true));
        memberAssetService.addMemberAsset(
                new AddMemberAssetRequestDto(
                        createTransactionRequestDto.getSellerId(),
                        createTransactionRequestDto.getTradingPair().getQuoteAsset().getId(),
                        createTransactionRequestDto.getPrice() * createTransactionRequestDto.getAmount(),
                        0,
                        false));
        memberAssetService.subtractMemberAsset(
                new SubtractMemberAssetRequestDto(
                        createTransactionRequestDto.getBuyerId(),
                        createTransactionRequestDto.getTradingPair().getQuoteAsset().getId(),
                        createTransactionRequestDto.getPrice() * createTransactionRequestDto.getAmount()));
        memberAssetService.subtractMemberAsset(
                new SubtractMemberAssetRequestDto(
                        createTransactionRequestDto.getSellerId(),
                        createTransactionRequestDto.getTradingPair().getBaseAsset().getId(),
                        createTransactionRequestDto.getAmount()));

        return new TransactionResponseDto(transactionRepository.save(Transaction.builder()
                .buyer(Member.builder().id(createTransactionRequestDto.getBuyerId()).build())
                .seller(Member.builder().id(createTransactionRequestDto.getSellerId()).build())
                .tradingPair(TradingPair.builder().id(createTransactionRequestDto.getTradingPair().getId()).build())
                .price(createTransactionRequestDto.getPrice())
                .amount(createTransactionRequestDto.getAmount())
                .build()));
    }

    public List<TransactionResponseDto> findAll() {
        return transactionRepository.findAll().stream().map(TransactionResponseDto::new).toList();
    }
}
