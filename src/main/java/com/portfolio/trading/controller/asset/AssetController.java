package com.portfolio.trading.controller.asset;

import com.portfolio.trading.data.dto.asset.AssetRequestDto;
import com.portfolio.trading.data.dto.asset.AssetResponseDto;
import com.portfolio.trading.data.entity.trading.Order;
import com.portfolio.trading.data.entity.trading.TradingPair;
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
@RequestMapping("/asset")
public class AssetController {

    private final AssetService assetService;

    @GetMapping()
    public ResponseEntity<AssetResponseDto> getAssetByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(assetService.getAsset(name));
    }

    @GetMapping("/list")
    public ResponseEntity<List<AssetResponseDto>> getAssets() {
        return ResponseEntity.status(HttpStatus.OK).body(assetService.findAll());
    }

    @PostMapping("/new")
    public ResponseEntity<AssetResponseDto> saveAsset(@RequestBody AssetRequestDto assetRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(assetService.saveAsset(assetRequestDto));
    }

    public void CreateOrder() {
        Order order;
        TradingPair pair;

        /*
        if (order.type == buy) {
            orders = Order.filter(o => o.type == sell && o.price <= order.price)
            orders.sort(o => o.price).sort(o.createdAt)
            orders.forEach(o => {
                amount = Min(order.amount - order.filledAmount, o.amount - o.filledAmount)
                order.filledAmount += amount;
                o.filledAmount += amount;
                transaction = createTransaction(order.member, o.member, pair, o.price, amount)

                buyersCoin = transaction.buyer.coins.find(coin => coin.id == pair.coin1.id)
                if (buyersCoin.amount <= 0) buyersCoin.averagePurchasedPrice = order.price
                else {
                    buyersCoin.averagePurchasedPrice = (buyersCoin.averagePurchasedPrice * buyersCoin.amount + order.price * order.amount) / (buyersCoin.amount + order.amount);
                }
                buyersCoin.amount += amount;


                transaction.seller.coins.find(coin => coin.id == pair.coin1.id).amount -= amount;
                transaction.buyer.coins.find(coin => coin.id == pair.coin2.id).amount -= amount * order.price;
                transaction.seller.coins.find(coin => coin.id == pair.coin2.id).amount += amount * order.price;
                if (o.amount == o.filledAmount) o.delete()
                if (order.amount == order.filledAmount) {
                    order.delete()
                    break
                }
                order.pair.lastPrice = order.price
            })
        }
        */
    }


}
