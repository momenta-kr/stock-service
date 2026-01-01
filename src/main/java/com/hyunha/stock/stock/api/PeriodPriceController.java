package com.hyunha.stock.stock.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyunha.stock.kis.infra.dto.DomesticStockPriceResponse;
import com.hyunha.stock.stock.application.CandleQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/stocks/v1")
@RestController
@RequiredArgsConstructor
public class PeriodPriceController {

    private final CandleQueryService candleQueryService;

    @GetMapping("/period-prices")
    public DomesticStockPriceResponse getCandles(
            @RequestParam String symbol,
            @RequestParam String periodType
            ) throws JsonProcessingException {
        return candleQueryService.getCandles(symbol, periodType);
    }
}
