package com.portfolio.trading.service.socket;

import com.portfolio.trading.data.dto.trading.CandleDto;
import com.portfolio.trading.data.dto.trading.OrderGatheringResponseDto;
import com.portfolio.trading.data.dto.trading.TradingPairResponseDto;
import com.portfolio.trading.data.dto.trading.TransactionResponseDto;
import com.portfolio.trading.data.entity.trading.OrderType;
import com.portfolio.trading.data.repository.trading.OrderRepository;
import com.portfolio.trading.data.repository.trading.TradingPairRepository;
import com.portfolio.trading.data.repository.trading.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SocketService {

    public final SimpMessageSendingOperations messagingTemplate;

    private final TradingPairRepository tradingPairRepository;
    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;


    public void sendTradingPairs() {
        // 자산 목록
        List<TradingPairResponseDto> tradingPairs = tradingPairRepository.findAllByOrderById().stream().map(TradingPairResponseDto::new).toList();
        messagingTemplate.convertAndSend("/topic/tradingPairs", tradingPairs);
    }

    public void sendCandles(Long tradingPairId) {
        // 차트 데이터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String from = "2023-02-05 00:00:00";
        String to = "2023-03-20 00:00:00";
        LocalDateTime startTime = LocalDateTime.parse(from, formatter);
        LocalDateTime endTime = LocalDateTime.parse(to, formatter);
        List<CandleDto> candleList = transactionRepository.findCandleList(tradingPairId, startTime, endTime);

        messagingTemplate.convertAndSend("/topic/candles/" + tradingPairId, candleList);
    }

    public void sendTransactions(Long tradingPairId) {
        List<TransactionResponseDto> transactions = transactionRepository.findAllByTradingPairIdOrderByUpdatedAtDesc(tradingPairId).stream().map(TransactionResponseDto::new).toList();
        messagingTemplate.convertAndSend("/topic/transactions/" + tradingPairId, transactions);
    }

    public void sendBuyOrderGatherings(Long tradingPairId) {
        List<Map<String, Double>> orderGatherings =
                orderRepository.findPriceAndSumOfAmountByPrice(OrderType.BUY.toString(), tradingPairId);
        List<OrderGatheringResponseDto> newOrderGatherings = new ArrayList<>();
        for (Map<String, Double> orderGathering : orderGatherings) {
            newOrderGatherings.add(new OrderGatheringResponseDto(orderGathering.get("price"), orderGathering.get("sum")));
        }
        messagingTemplate.convertAndSend("/topic/orders/gathering/buy/" + tradingPairId, newOrderGatherings);
    }

    public void sendSellOrderGatherings(Long tradingPairId) {
        List<Map<String, Double>> orderGatherings = orderRepository.findPriceAndSumOfAmountByPrice(OrderType.SELL.toString(), tradingPairId);
        List<OrderGatheringResponseDto> newOrderGatherings = new ArrayList<>();
        for (Map<String, Double> orderGathering : orderGatherings) {
            newOrderGatherings.add(new OrderGatheringResponseDto(orderGathering.get("price"), orderGathering.get("sum")));
        }
        messagingTemplate.convertAndSend("/topic/orders/gathering/sell/" + tradingPairId, newOrderGatherings);
    }
}
