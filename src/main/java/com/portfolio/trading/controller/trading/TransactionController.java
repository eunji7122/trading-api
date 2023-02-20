package com.portfolio.trading.controller.trading;

import com.portfolio.trading.data.dto.trading.CandleDto;
import com.portfolio.trading.data.dto.trading.TransactionResponseDto;
import com.portfolio.trading.service.trading.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getTransactions() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAll());
    }

//    @GetMapping("/candle-list")
//    public ResponseEntity<List<CandleDto>> getCandleList(
//            @RequestParam Long tradingPairId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
//        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findCandleList(tradingPairId, startTime, endTime));
//    }

    @GetMapping("/candles")
    public ResponseEntity<List<CandleDto>> getCandleList(
            @RequestParam Long tradingPairId,
            @RequestParam String from,
            @RequestParam String to) {
        // 수정 필요
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(from, formatter);
        LocalDateTime endTime = LocalDateTime.parse(to, formatter);

        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findCandleList(tradingPairId, startTime, endTime));
    }
}
