package com.portfolio.trading.controller.trading;

import com.portfolio.trading.data.dto.trading.TransactionResponseDto;
import com.portfolio.trading.service.trading.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
