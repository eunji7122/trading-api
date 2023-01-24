package com.portfolio.trading.data.entity.transaction;

import com.portfolio.trading.data.entity.coin.Coin;
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
public class Buying {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long amount;

}
