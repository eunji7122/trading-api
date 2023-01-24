package com.portfolio.trading.data.entity.coin;

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
public class MemberCoin {

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
    private int amount;

    @Column(nullable = false)
    private int averagePurchasedPrice;

}
