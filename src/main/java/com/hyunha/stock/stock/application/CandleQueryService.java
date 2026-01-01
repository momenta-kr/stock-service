package com.hyunha.stock.stock.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyunha.stock.kis.infra.KisClient;
import com.hyunha.stock.kis.infra.dto.CandlesResponse;
import com.hyunha.stock.kis.infra.dto.DomesticStockPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CandleQueryService {

    private final CandleCacheReader candleCacheReader;
    private final CandleCacheWriter candleCacheWriter;
    private final KisClient kisClient;


    public DomesticStockPriceResponse getCandles(String symbol, String period) throws JsonProcessingException {
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().minusDays(100);

        String key = "stock:symbol:%s:%s:%s:%s".formatted(symbol, period, from.toString(), to.toString());
        Optional<DomesticStockPriceResponse> optionalCandles = candleCacheReader.getCandles(key);

        boolean cacheMiss = optionalCandles.isEmpty();
        if (cacheMiss) {
            // TODO: save
            DomesticStockPriceResponse domesticStockPriceResponse = kisClient.fetchDomesticStockPeriodPrices(symbol, period, from.toString(), to.toString());
            candleCacheWriter.save(key, domesticStockPriceResponse);
            return domesticStockPriceResponse;
        } else {
            return optionalCandles.get();
        }

    }
}

