package com.portfolio.trading.service.trading;

import com.portfolio.trading.data.dto.asset.MemberAssetResponseDto;
import com.portfolio.trading.data.dto.trading.CreateTransactionRequestDto;
import com.portfolio.trading.data.dto.trading.OrderRequestDto;
import com.portfolio.trading.data.dto.trading.OrderResponseDto;
import com.portfolio.trading.data.dto.trading.TradingPairResponseDto;
import com.portfolio.trading.data.entity.trading.Order;
import com.portfolio.trading.data.entity.trading.OrderType;
import com.portfolio.trading.data.repository.member.MemberRepository;
import com.portfolio.trading.data.repository.trading.OrderRepository;
import com.portfolio.trading.data.repository.trading.TradingPairRepository;
import com.portfolio.trading.service.asset.MemberAssetService;
import com.portfolio.trading.service.member.MemberService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TransactionService transactionService;
    private final MemberService memberService;
    private final MemberAssetService memberAssetService;
    private final TradingPairService tradingPairService;
    private final MemberRepository memberRepository;
    private final TradingPairRepository tradingPairRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        // 로그인 유저 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        TradingPairResponseDto tradingPair = tradingPairService.findById(orderRequestDto.getTradingPairId());
        double filledAmount = 0;

        if (orderRequestDto.getType() == OrderType.BUY) {
            Long buyerId = memberService.getMemberId(userDetails.getUsername());
            MemberAssetResponseDto buyersQuoteAsset =
                    memberAssetService.findAllByMemberIdAndAssetId(buyerId, tradingPair.getQuoteAsset().getId());
            if (buyersQuoteAsset.getAmount() < orderRequestDto.getAmount() * orderRequestDto.getPrice()) {
                // throw exception
                throw new RuntimeException();
            }
            double amount = orderRequestDto.getAmount();
            List<Order> orders = orderRepository.findByTypeAndPriceLessThanEqualOrderByPrice(OrderType.SELL, orderRequestDto.getPrice());
            orders.sort((Order order1, Order order2) -> order1.getPrice() <= order2.getPrice() ? -1 : 1);
            for (Order o : orders) {
                double transactionAmount = Math.min(amount - filledAmount, o.getAmount() - o.getFilledAmount());
                o.setFilledAmount(o.getFilledAmount() + transactionAmount);
                filledAmount += transactionAmount;

                // begin transaction
                if (o.getFilledAmount() == o.getAmount()) {
                    orderRepository.delete(o);
                } else {
                    orderRepository.save(o);
                }
                transactionService.createTransaction(
                        new CreateTransactionRequestDto(buyerId, o.getMember().getId(), tradingPair, o.getPrice(), transactionAmount));
                tradingPairService.updateLastPrice(tradingPair.getId(), o.getPrice());
                tradingPairService.updateChangeRate(tradingPair.getId());
                tradingPairService.updateTradingAmount(tradingPair.getId());
                tradingPairService.updateTradingValue(tradingPair.getId());
                tradingPairService.updateHighestPrice(tradingPair.getId());
                tradingPairService.updateLowestPrice(tradingPair.getId());
            }
            if (amount > filledAmount) {
                Order order = orderRepository.save(
                        Order.builder()
                                .member(memberRepository.getReferenceById(buyerId))
                                .tradingPair(tradingPairRepository.getReferenceById(orderRequestDto.getTradingPairId()))
                                .type(orderRequestDto.getType())
                                .price(orderRequestDto.getPrice())
                                .amount(orderRequestDto.getAmount())
                                .filledAmount(filledAmount)
                                .build());

                return new OrderResponseDto(orderRepository.findById(order.getId()).get());
            }
        } else {
            Long sellerId = memberService.getMemberId(userDetails.getUsername());
            MemberAssetResponseDto sellersBaseAsset =
                    memberAssetService.findAllByMemberIdAndAssetId(sellerId, tradingPair.getBaseAsset().getId());
            if (sellersBaseAsset.getAmount() < orderRequestDto.getAmount()) {
                // throw exception
                throw new RuntimeException();
            }
            double amount = orderRequestDto.getAmount();
            List<Order> orders = orderRepository.findByTypeAndPriceGreaterThanEqualOrderByPrice(OrderType.BUY, orderRequestDto.getPrice());
            orders.sort((Order order1, Order order2) -> order1.getPrice() >= order2.getPrice() ? -1 : 1);
            for (Order o : orders) {
                double transactionAmount = Math.min(amount - filledAmount, o.getAmount() - o.getFilledAmount());
                o.setFilledAmount(o.getFilledAmount() + transactionAmount);
                filledAmount += transactionAmount;

                // begin transaction
                if (o.getFilledAmount() == o.getAmount()) {
                    orderRepository.delete(o);
                } else {
                    orderRepository.save(o);
                }
                transactionService.createTransaction(
                        new CreateTransactionRequestDto(sellerId, o.getMember().getId(), tradingPair, o.getPrice(), transactionAmount));
                tradingPairService.updateLastPrice(tradingPair.getId(), o.getPrice());
                tradingPairService.updateChangeRate(tradingPair.getId());
                tradingPairService.updateTradingAmount(tradingPair.getId());
                tradingPairService.updateTradingValue(tradingPair.getId());
                tradingPairService.updateHighestPrice(tradingPair.getId());
                tradingPairService.updateLowestPrice(tradingPair.getId());
            }
            if (amount > filledAmount) {
                Order order = orderRepository.save(
                        Order.builder()
                                .member(memberRepository.getReferenceById(sellerId))
                                .tradingPair(tradingPairRepository.getReferenceById(orderRequestDto.getTradingPairId()))
                                .type(orderRequestDto.getType())
                                .price(orderRequestDto.getPrice())
                                .amount(orderRequestDto.getAmount())
                                .filledAmount(filledAmount)
                                .build());
                return new OrderResponseDto(orderRepository.findById(order.getId()).get());
            }
        }

        return null;
    }
}
