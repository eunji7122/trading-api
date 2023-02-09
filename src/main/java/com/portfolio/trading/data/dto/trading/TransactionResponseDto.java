package com.portfolio.trading.data.dto.trading;

import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.data.entity.trading.TradingPair;
import com.portfolio.trading.data.entity.trading.Transaction;
import lombok.Getter;

@Getter
public class TransactionResponseDto {

    private final Long id;
    private final Member buyer;
    private final Member seller;
    private final TradingPair tradingPair;
    private final double price;
    private final double amount;

    public TransactionResponseDto(Transaction transaction) {
        this.id = transaction.getId();
        this.buyer = transaction.getBuyer();
        this.seller = transaction.getSeller();
        this.tradingPair = transaction.getTradingPair();
        this.price = transaction.getPrice();
        this.amount = transaction.getAmount();
    }
}
