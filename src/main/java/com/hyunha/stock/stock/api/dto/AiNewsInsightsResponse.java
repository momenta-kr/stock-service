package com.hyunha.stock.stock.api.dto;

import java.util.List;

public record AiNewsInsightsResponse(
        long total,
        List<KeyCountDto> sentiments,
        List<KeyCountDto> topCategories,
        List<KeyCountDto> topDomains
) {

    public record KeyCountDto(String key, long count) {}

}
