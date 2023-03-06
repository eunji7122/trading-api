package com.portfolio.trading.service.socket;

import com.portfolio.trading.data.dto.trading.CandleDto;
import com.portfolio.trading.data.dto.trading.TradingPairResponseDto;
import com.portfolio.trading.service.trading.TradingPairService;
import com.portfolio.trading.service.trading.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SocketService {

    public final SimpMessageSendingOperations messagingTemplate;

    private final TradingPairService tradingPairService;
    private final TransactionService transactionService;

    public void sendTradingPairs() {
        List<TradingPairResponseDto> tradingPairs = tradingPairService.findAllOrderById();
        messagingTemplate.convertAndSend("/topic/tradingPairs", tradingPairs);
    }

    public void sendCandles(Long tradingPairId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String from = "2023-02-05 00:00:00";
        String to = "2023-03-06 00:00:00";
        LocalDateTime startTime = LocalDateTime.parse(from, formatter);
        LocalDateTime endTime = LocalDateTime.parse(to, formatter);
        List<CandleDto> candleList = transactionService.findCandleList(tradingPairId, startTime, endTime);

        messagingTemplate.convertAndSend("/topic/candles/" + tradingPairId, candleList);
    }

}
