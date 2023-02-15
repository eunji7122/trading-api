package com.portfolio.trading.data.entity.trading;

import com.portfolio.trading.data.entity.asset.Asset;
import com.portfolio.trading.data.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class TradingPair extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "base_asset_id")
    private Asset baseAsset;

    @ManyToOne
    @JoinColumn(name = "quote_asset_id")
    private Asset quoteAsset;

    @Column(nullable = false)
    private double lastPrice;

    // 전일대비(변동률)
    @Column(nullable = false)
    private double changeRate;

    // 거래대금
    @Column(nullable = false)
    private double tradingValue;
}
