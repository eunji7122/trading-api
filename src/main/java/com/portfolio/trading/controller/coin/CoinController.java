package com.portfolio.trading.controller.coin;

import com.portfolio.trading.data.dto.coin.CoinRequestDto;
import com.portfolio.trading.data.dto.coin.CoinResponseDto;
import com.portfolio.trading.service.coin.CoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/coin")
public class CoinController {

    private final CoinService coinService;

    @GetMapping()
    public ResponseEntity<CoinResponseDto> getCoinByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(coinService.getCoin(name));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CoinResponseDto>> getCoins() {
        return ResponseEntity.status(HttpStatus.OK).body(coinService.findAll());
    }

    @PostMapping("/new")
    public ResponseEntity<CoinResponseDto> saveCoin(@RequestBody CoinRequestDto coinRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(coinService.saveCoin(coinRequestDto));
    }


}
