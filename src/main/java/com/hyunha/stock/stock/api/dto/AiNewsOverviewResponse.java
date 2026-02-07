package com.hyunha.stock.stock.api.dto;

public record AiNewsOverviewResponse(
        AiNewsInsightsResponse insights,
        AiNewsFeedResponse feed
) {}