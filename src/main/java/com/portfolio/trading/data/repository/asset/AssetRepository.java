package com.portfolio.trading.data.repository.asset;

import com.portfolio.trading.data.entity.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByName(String name);
}
