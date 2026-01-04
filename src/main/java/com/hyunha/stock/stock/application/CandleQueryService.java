package com.hyunha.stock.stock.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyunha.stock.kis.infra.KisClient;
import com.hyunha.stock.kis.infra.dto.DomesticStockPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CandleQueryService {

    private final CandleCacheReader candleCacheReader;
    private final CandleCacheWriter candleCacheWriter;
    private final KisClient kisClient;

    private static final DateTimeFormatter KIS_DATE = DateTimeFormatter.BASIC_ISO_DATE; // yyyyMMdd



    public DomesticStockPriceResponse getCandles(String symbol, String period, String fromStr, String toStr)
            throws JsonProcessingException {

        LocalDate to = (toStr != null && !toStr.isBlank()) ? LocalDate.parse(toStr) : LocalDate.now();
        LocalDate from = (fromStr != null && !fromStr.isBlank()) ? LocalDate.parse(fromStr) : defaultFrom(to, period);

        // ✅ from/to 뒤집힘 방지
        if (from.isAfter(to)) {
            LocalDate tmp = from;
            from = to;
            to = tmp;
        }

        // (선택) 너무 넓게 요청하는 거 방지: KIS가 100개 제한이면 기간을 제한해도 됨
        // long days = ChronoUnit.DAYS.between(from, to);
        // if (days > 2000) { ... }

        String key = "stock:candles:%s:%s:%s:%s".formatted(symbol, period, from, to);
        Optional<DomesticStockPriceResponse> cached = candleCacheReader.getCandles(key);

        if (cached.isPresent()) return cached.get();

        // ✅ KIS에는 yyyyMMdd로 보냄
        String kisFrom = from.format(KIS_DATE);
        String kisTo = to.format(KIS_DATE);

        DomesticStockPriceResponse res =
                kisClient.fetchDomesticStockPeriodPrices(symbol, period, kisFrom, kisTo);

        candleCacheWriter.save(key, res);
        return res;
    }

    private LocalDate defaultFrom(LocalDate to, String period) {
        // ✅ 100개 제한 대응: period별로 “대충 100봉을 커버할 정도의 일수”를 잡음
        // (정확한 봉 개수 보장은 KIS API 스펙에 따라 조정)
        return switch (period) {
            case "D" -> to.minusDays(140);   // 영업일 고려
            case "W" -> to.minusDays(900);
            case "M" -> to.minusDays(2500);
            case "Y" -> to.minusDays(6000);
            default -> to.minusDays(140);
        };
    }
}

