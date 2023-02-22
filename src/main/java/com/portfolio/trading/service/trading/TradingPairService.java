package com.portfolio.trading.service.trading;

import com.portfolio.trading.data.dto.trading.TradingPairResponseDto;
import com.portfolio.trading.data.dto.trading.TransactionResponseDto;
import com.portfolio.trading.data.entity.trading.TradingPair;
import com.portfolio.trading.data.repository.trading.TradingPairRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TradingPairService {

    private final TradingPairRepository tradingPairRepository;
    private final TransactionService transactionService;

    public List<TradingPairResponseDto> findAll() {
        return tradingPairRepository.findAll().stream().map(TradingPairResponseDto::new).toList();
    }

    public TradingPairResponseDto findById(Long id) {
        return new TradingPairResponseDto(tradingPairRepository.findById(id).get());
    }

    // 현재가 계산
    public void updateLastPrice(Long id, double price) {
        TradingPair tradingPair = tradingPairRepository.getReferenceById(id);
        tradingPair.setLastPrice(price);
        tradingPairRepository.save(tradingPair);
    }

    // 전일대비 변동률 계산
    public void updateChangeRate(Long id) {
        TradingPair tradingPair = tradingPairRepository.getReferenceById(id);

        LocalDateTime startTime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0));
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));

        List<TransactionResponseDto> transactions = transactionService.findAllByUpdatedAtBetween(startTime, endTime)
                .stream().filter(item -> Objects.equals(item.getTradingPair().getId(), id)).toList();

        if (transactions.size() == 0) { // 전날 거래된 가격이 없을 경우
            double changeRate = tradingPair.getLastPrice() * 100;
            tradingPair.setChangeRate(changeRate);
            tradingPairRepository.save(tradingPair);
        } else {
            double yesterdayLastPrice = transactions.get(transactions.size() - 1).getPrice();
            double changeRate = (tradingPair.getLastPrice() - yesterdayLastPrice) / yesterdayLastPrice * 100;
            tradingPair.setChangeRate(Math.round(changeRate * 100 / 100));
            tradingPairRepository.save(tradingPair);
        }
    }

    // 거래량 계산
    public void updateTradingAmount(Long id) {
        TradingPair tradingPair = tradingPairRepository.getReferenceById(id);
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        List<TransactionResponseDto> transactions = transactionService.findAllByUpdatedAtAfter(today);
        double tradingAmount = 0;
        for (TransactionResponseDto transaction : transactions) {
            tradingAmount += transaction.getAmount();
        }
        tradingPair.setTradingAmount(tradingAmount);
        tradingPairRepository.save(tradingPair);
    }

    // 거래대금 계산
    public void updateTradingValue(Long id) {
        TradingPair tradingPair = tradingPairRepository.getReferenceById(id);

        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        List<TransactionResponseDto> transactions = transactionService.findAllByUpdatedAtAfter(today);
        double tradingValue = 0;
        for (TransactionResponseDto transaction : transactions) {
            tradingValue += transaction.getAmount() * transaction.getPrice();
        }
        tradingPair.setTradingValue(tradingValue);
        tradingPairRepository.save(tradingPair);
    }

    // 당일 고가 계산
    public void updateHighestPrice(Long id) {
        TradingPair tradingPair = tradingPairRepository.getReferenceById(id);

        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        double price = transactionService.getHighPriceAtTodayByTradingPair(id, today);
        tradingPair.setHighestPrice(price);
        tradingPairRepository.save(tradingPair);
    }

    // 당일 저가 계산
    public void updateLowestPrice(Long id) {
        TradingPair tradingPair = tradingPairRepository.getReferenceById(id);

        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        double price = transactionService.getLowPriceAtTodayByTradingPair(id, today);
        tradingPair.setLowestPrice(price);
        tradingPairRepository.save(tradingPair);
    }
}
