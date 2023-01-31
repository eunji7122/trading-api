package com.portfolio.trading.service.asset;

import com.portfolio.trading.data.dto.asset.AssetRequestDto;
import com.portfolio.trading.data.dto.asset.AssetResponseDto;
import com.portfolio.trading.data.entity.asset.Asset;
import com.portfolio.trading.data.repository.asset.AssetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetResponseDto getAsset(String name) {
        Asset asset = assetRepository.findByName(name).orElse(null);
        return new AssetResponseDto(asset);
    }

    public AssetResponseDto saveAsset(AssetRequestDto assetRequestDto) {
        Asset savedAsset = assetRepository.save(assetRequestDto.toEntity());
        return new AssetResponseDto(savedAsset);
    }

    public AssetResponseDto findById(Long id) {
        Asset findAsset = assetRepository.findById(id).orElse(null);
        return new AssetResponseDto(findAsset);
    }

    public List<AssetResponseDto> findAll() {
        return assetRepository.findAll().stream().map(AssetResponseDto::new).toList();
    }

}
