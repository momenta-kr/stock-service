package com.hyunha.stock.stock.api.dto;

public record GetThemesResponse(
        String themeCode,
        String themeName,
        long stockCount
) {
}
