package com.hyunha.stock.stock.application;

import com.hyunha.stock.stock.api.dto.GetThemeResponse;
import com.hyunha.stock.stock.domain.port.out.StockCacheReader;
import com.hyunha.stock.stock.domain.port.out.ThemeQueryPort;
import com.hyunha.stock.stock.infra.jpa.dto.ThemeResponse;
import com.hyunha.stock.stock.infra.redis.dto.DomesticStockCurrentPriceResponse;
import com.hyunha.stock.stock.infra.redis.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ThemeQueryService {

    private final ThemeQueryPort themeQueryPort;
    private final StockCacheReader stockCacheReader;

    public List<GetThemeResponse> getThemes() {
        List<ThemeResponse> themeResponses = themeQueryPort.getThemes();

        // ✅ 1) 전체 종목코드 중복 제거
        List<String> uniqueStockCodes = themeResponses.stream()
                .flatMap(tr -> tr.stockCodes().stream())
                .distinct()
                .toList();

        // ✅ 2) Redis Key도 중복 없는 상태로 생성
        List<String> redisKeys = uniqueStockCodes.stream()
                .map(stockCode -> RedisKey.STOCK_INFO.getKey() + ":" + stockCode)
                .toList();

        List<DomesticStockCurrentPriceResponse> prices =
                stockCacheReader.getDomesticStockCurrentPrices(redisKeys);

        // ✅ 3) 응답을 stockShortCode 기준으로 Map으로 압축(중복 제거)
        Map<String, DomesticStockCurrentPriceResponse.Output> outputByStockCode =
                prices.stream()
                        .map(DomesticStockCurrentPriceResponse::getOutput)
                        .filter(Objects::nonNull)
                        .filter(o -> o.getStockShortCode() != null && !o.getStockShortCode().isBlank())
                        .collect(Collectors.toMap(
                                DomesticStockCurrentPriceResponse.Output::getStockShortCode,
                                Function.identity(),
                                (a, b) -> a // 중복이면 첫 번째 유지(원하면 최신 선택 로직으로 변경 가능)
                        ));

        return themeResponses.stream()
                .map(themeResponse -> {
                    // ✅ 4) 테마 내부 stockCodes도 혹시 모를 중복 제거(순서 유지)
                    List<String> themeStockCodes = themeResponse.stockCodes().stream()
                            .filter(Objects::nonNull)
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .distinct()
                            .toList();

                    // ✅ 5) 테마 종목코드 순서대로 Output을 안전하게 매핑
                    List<DomesticStockCurrentPriceResponse.Output> outputs = themeStockCodes.stream()
                            .map(outputByStockCode::get)
                            .filter(Objects::nonNull)
                            .toList();

                    // ✅ 6) 평균 등락률도 outputs 기반으로 계산(중복 없음)
                    OptionalDouble avg = outputs.stream()
                            .map(DomesticStockCurrentPriceResponse.Output::getChangeRateFromPreviousDay)
                            .filter(Objects::nonNull)
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .mapToDouble(this::safeParseDouble)
                            .average();

                    return new GetThemeResponse(
                            themeResponse.themeCode(),
                            themeResponse.themeName(),
                            avg.isPresent() ? avg.getAsDouble() : null,
                            outputs
                    );
                })
                .toList();
    }

    private double safeParseDouble(String v) {
        try {
            return Double.parseDouble(v.replace(",", ""));
        } catch (Exception e) {
            return 0.0;
        }
    }
}
