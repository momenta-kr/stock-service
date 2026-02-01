package com.hyunha.stock.stock.api.dto;

import com.hyunha.stock.stock.infra.redis.dto.DomesticStockCurrentPriceResponse;

import java.util.List;

public record GetThemeResponse(
        String themeCode,
        String themeName,
        Double averageChangeRateFromPreviousDay,
        List<DomesticStockCurrentPriceResponse.Output> stockInfoList
        ) {
}
