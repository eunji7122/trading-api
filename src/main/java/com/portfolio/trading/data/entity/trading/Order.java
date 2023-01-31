package com.portfolio.trading.data.entity.trading;

import com.portfolio.trading.data.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "trading_pair_id")
    private TradingPair tradingPair;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private double filledAmount;
}
