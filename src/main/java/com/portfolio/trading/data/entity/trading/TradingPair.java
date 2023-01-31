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
    @JoinColumn(name = "coin1_id")
    private Asset asset1;

    @ManyToOne
    @JoinColumn(name = "coin2_id")
    private Asset asset2;

    @Column(nullable = false)
    private double lastPrice;
}
