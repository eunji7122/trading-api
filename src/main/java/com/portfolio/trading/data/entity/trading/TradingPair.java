package com.portfolio.trading.data.entity.trading;

import com.portfolio.trading.data.entity.asset.Asset;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class TradingPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "base_asset_id")
    private Asset baseAsset;

    @ManyToOne
    @JoinColumn(name = "quote_asset2_id")
    private Asset quoteAsset;

    @Column(nullable = false)
    private double lastPrice;
}
