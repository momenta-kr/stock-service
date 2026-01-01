package com.hyunha.stock.kis.infra.dto;

public record CandleRefreshTask(
    String symbol,
    String tf,
    String priority,
    long requestedAt
) {}
