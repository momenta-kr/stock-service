package com.hyunha.stock.kis.infra.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public record CandlesResponse(
        String excd,
        String symbol,
        int nmin,
        List<Candle> candles
) {

    private static final ObjectMapper OM = new ObjectMapper();

    public CandlesResponse trim(int limit) {
        if (candles == null || candles.size() <= limit) return this;
        return new CandlesResponse(excd, symbol, nmin, candles.subList(0, limit));
    }

    public String toJson() {
        try {
            return OM.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
