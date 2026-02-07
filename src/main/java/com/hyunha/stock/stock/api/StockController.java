package com.hyunha.stock.stock.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyunha.stock.stock.api.dto.GetDomesticStockCurrentPriceOutput;
import com.hyunha.stock.stock.api.dto.GetInvestmentOpinionResponse;
import com.hyunha.stock.stock.api.dto.GetVolumeRankingResponse;
import com.hyunha.stock.stock.application.StockQueryService;
import com.hyunha.stock.stock.infra.redis.dto.DomesticStockCurrentPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/api/stocks/v1")
@RestController
public class StockController {

    private final StockQueryService stockQueryService;

    @GetMapping("/investment-opinion")
    public List<GetInvestmentOpinionResponse> getInvestmentOpinion(@RequestParam String symbol) {
        return stockQueryService.getInvestmentOpinion(symbol);
    }

    @GetMapping("/volume-ranking")
    public List<GetVolumeRankingResponse> getVolumeRankings() throws JsonProcessingException {
        return stockQueryService.getVolumeRankings();
    }

    @GetMapping("/{stockCode}")
    public GetDomesticStockCurrentPriceOutput getStock(@PathVariable String stockCode) {
        return stockQueryService.getStock(stockCode);
    }

    @GetMapping("/{stockCode}/news")
    public void getNews(@PathVariable String stockCode) {
        stockQueryService.getNews(stockCode);
    }
}
