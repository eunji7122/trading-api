package com.portfolio.trading.data.repository.asset;

import com.portfolio.trading.data.entity.asset.MemberAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAssetRepository extends JpaRepository<MemberAsset, Long> {

    List<MemberAsset> findAllByMemberId(Long memberId);
}
