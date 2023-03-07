package com.portfolio.trading.controller.trading;

import com.portfolio.trading.data.dto.member.MemberResponseDto;
import com.portfolio.trading.data.dto.trading.OrderGatheringResponseDto;
import com.portfolio.trading.data.dto.trading.OrderRequestDto;
import com.portfolio.trading.data.dto.trading.OrderResponseDto;
import com.portfolio.trading.data.entity.trading.OrderType;
import com.portfolio.trading.service.member.MemberService;
import com.portfolio.trading.service.trading.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(orderRequestDto));
    }

    @GetMapping()
    public ResponseEntity<List<OrderResponseDto>> getOrders(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MemberResponseDto member = memberService.findByEmail(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAllByMemberId(member.getId()));
    }

    @GetMapping("/gathering/buy")
    public ResponseEntity<List<OrderGatheringResponseDto>> getBuyOrderGathering(@RequestParam Long tradingPairId) {
        List<OrderGatheringResponseDto> orderGatherings = orderService.findPriceAndSumOfAmountGroupByPrice(OrderType.BUY, tradingPairId);
        return ResponseEntity.status(HttpStatus.OK).body(orderGatherings);
    }

    @GetMapping("/gathering/sell")
    public ResponseEntity<List<OrderGatheringResponseDto>> getSellOrderGathering(@RequestParam Long tradingPairId) {
        List<OrderGatheringResponseDto> orderGatherings = orderService.findPriceAndSumOfAmountGroupByPrice(OrderType.SELL, tradingPairId);
        return ResponseEntity.status(HttpStatus.OK).body(orderGatherings);
    }
}
