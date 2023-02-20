package com.portfolio.trading.data.repository.trading;

import com.portfolio.trading.data.dto.trading.CandleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class TransactionRepositoryImpl implements CustomTransactionRepository {

    @Autowired
    @Lazy
    TransactionRepository transactionRepository;

    @Override
    public List<CandleDto> findCandleList(Long tradingPairId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Map<String, Object>> highestAndLowestPrices = transactionRepository.findHighestAndLowestPricesByDay(tradingPairId, startTime, endTime);
        List<Map<String, Object>> startPrices = transactionRepository.findStartPriceByDay(tradingPairId, startTime, endTime);
        List<Map<String, Object>> endPrices = transactionRepository.findEndPriceByDay(tradingPairId, startTime, endTime);

        List<CandleDto> candleDtoList = new ArrayList<>();
        for (int i = 0; i < highestAndLowestPrices.size(); i++) {
            candleDtoList.add(
                    new CandleDto(
                            (Double) startPrices.get(i).get("price"),
                            (Double) endPrices.get(i).get("price"),
                            (Double) highestAndLowestPrices.get(i).get("highestPrice"),
                            (Double) highestAndLowestPrices.get(i).get("lowestPrice"),
                            (Timestamp) highestAndLowestPrices.get(i).get("day"),
                            1));
        }

        return candleDtoList;
    }
}
