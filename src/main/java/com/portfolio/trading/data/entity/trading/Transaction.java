package com.portfolio.trading.data.entity.trading;

import com.portfolio.trading.data.entity.base.BaseEntity;
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
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne
    @JoinColumn(name = "trading_pair_id")
    private TradingPair tradingPair;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double amount;
}
