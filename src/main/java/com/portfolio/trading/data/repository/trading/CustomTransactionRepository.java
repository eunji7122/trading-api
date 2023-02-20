package com.portfolio.trading.data.repository.trading;

import com.portfolio.trading.data.dto.trading.CandleDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomTransactionRepository {

    List<CandleDto> findCandleList(Long tradingPairId, LocalDateTime startTime, LocalDateTime endTime);
}
