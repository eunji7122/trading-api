package com.portfolio.trading.service.trading;

import com.portfolio.trading.data.dto.trading.TradingPairResponseDto;
import com.portfolio.trading.data.entity.trading.TradingPair;
import com.portfolio.trading.data.repository.trading.TradingPairRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TradingPairService {

    private final TradingPairRepository tradingPairRepository;

    public List<TradingPairResponseDto> findAll() {
        return tradingPairRepository.findAll().stream().map(TradingPairResponseDto::new).toList();
    }

    public TradingPairResponseDto findById(Long id) {
        return new TradingPairResponseDto(tradingPairRepository.findById(id).get());
    }

    public TradingPairResponseDto updateLastPrice(Long id, double price) {
        TradingPair tradingPair = tradingPairRepository.getReferenceById(id);
        tradingPair.setLastPrice(price);
        return new TradingPairResponseDto(tradingPairRepository.save(tradingPair));
    }
}