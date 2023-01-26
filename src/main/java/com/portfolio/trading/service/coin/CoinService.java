package com.portfolio.trading.service.coin;

import com.portfolio.trading.data.dto.coin.CoinRequestDto;
import com.portfolio.trading.data.dto.coin.CoinResponseDto;
import com.portfolio.trading.data.entity.coin.Coin;
import com.portfolio.trading.data.repository.coin.CoinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CoinService {

    private final CoinRepository coinRepository;

    public CoinResponseDto getCoin(String name) {
        Coin coin = coinRepository.findByName(name).orElse(null);
        return new CoinResponseDto(coin);
    }

    public CoinResponseDto saveCoin(CoinRequestDto coinRequestDto) {
        Coin savedCoin = coinRepository.save(coinRequestDto.toEntity());
        return new CoinResponseDto(savedCoin);
    }

    public CoinResponseDto findById(Long id) {
        Coin findCoin = coinRepository.findById(id).orElse(null);
        return new CoinResponseDto(findCoin);
    }

    public List<CoinResponseDto> findAll() {
        return coinRepository.findAll().stream().map(CoinResponseDto::new).toList();
    }

}
