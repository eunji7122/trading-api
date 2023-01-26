package com.portfolio.trading.controller.coin;

import com.portfolio.trading.common.ApiResponse;
import com.portfolio.trading.data.dto.coin.CoinRequestDto;
import com.portfolio.trading.data.dto.coin.CoinResponseDto;
import com.portfolio.trading.data.dto.response.ListResult;
import com.portfolio.trading.data.dto.response.SingleResult;
import com.portfolio.trading.service.coin.CoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/coin")
public class CoinController {

    private final CoinService coinService;

    @GetMapping()
    public SingleResult<CoinResponseDto> getCoinByName(@RequestParam String name) {
        return ApiResponse.getSingleResult(coinService.getCoin(name));
    }

    @GetMapping("/list")
    public ListResult<CoinResponseDto> getCoins() {
        return ApiResponse.getListResult(coinService.findAll());
    }

    @PostMapping("/new")
    SingleResult<CoinResponseDto> saveCoin(@RequestBody CoinRequestDto coinRequestDto) {
        return ApiResponse.getSingleResult(coinService.saveCoin(coinRequestDto));
    }


}
