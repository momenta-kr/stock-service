package com.hyunha.stock.kis.infra.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Candle(
            LocalDateTime kstDateTime,
            BigDecimal open,
            BigDecimal high,
            BigDecimal low,
            BigDecimal close,
            long volume,
            long amount
    ) {}