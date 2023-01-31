package com.portfolio.trading.data.entity.asset;

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
public class MemberAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coin_id")
    private Asset asset;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private double averagePurchasedPrice;

}
