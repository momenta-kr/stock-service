package com.hyunha.stock.stock.infra.jpa.dto;

import java.util.List;

public record ThemeResponse(
        String themeCode,
        String themeName,
        long stockCount,
        List<Stock> stocks
) {

    public record Stock(String stockCode, String stockName){}
}
