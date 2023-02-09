package com.portfolio.trading.controller.asset;

import com.portfolio.trading.data.dto.asset.CreateAssetRequestDto;
import com.portfolio.trading.data.dto.asset.AssetResponseDto;
import com.portfolio.trading.service.asset.AssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    @GetMapping("/{name}")
    public ResponseEntity<AssetResponseDto> getAssetByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(assetService.getAsset(name));
    }

    @GetMapping()
    public ResponseEntity<List<AssetResponseDto>> getAssets() {
        return ResponseEntity.status(HttpStatus.OK).body(assetService.findAll());
    }

    @PostMapping()
    public ResponseEntity<AssetResponseDto> createAsset(@RequestBody CreateAssetRequestDto createAssetRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(assetService.createAsset(createAssetRequestDto));
    }

}
