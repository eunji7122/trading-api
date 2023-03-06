package com.portfolio.trading.controller.trading;

import com.portfolio.trading.data.dto.trading.TradingPairResponseDto;
import com.portfolio.trading.service.trading.TradingPairService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tradingPairs")
public class TradingPairController {

    private final TradingPairService tradingPairService;

    @GetMapping()
    public ResponseEntity<List<TradingPairResponseDto>> getTradingPairs() {
        return ResponseEntity.status(HttpStatus.OK).body(tradingPairService.findAllOrderById());
    }
}
