package com.hyunha.stock.stock.api;

import com.hyunha.stock.stock.application.TradeRankingQueryService;
import com.hyunha.stock.stock.infra.redis.dto.SymbolFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/stocks/v1")
@RestController
public class MomentumController {

    private final TradeRankingQueryService tradeRankingQueryService;

    @GetMapping("/momentum")
    public List<SymbolFeature> getTradeRanking() {
        return tradeRankingQueryService.getRanking();
    }

    @GetMapping("/momentum/{symbol}")
    public SymbolFeature getTradeRankingBySymbol(@PathVariable String symbol) {
        return tradeRankingQueryService.getRanking().stream()
                .filter(feature -> feature.getSymbol().equals(symbol))
                .findFirst()
                .orElseThrow();
    }


}
