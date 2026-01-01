package com.hyunha.stock.kis.application;

public record KisClientHeaders(
        String appKey,
        String appSecret,
        String custType
) {}
