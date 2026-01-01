package com.hyunha.stock.stock.infra.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunha.stock.kis.infra.dto.DomesticStockPriceResponse;
import com.hyunha.stock.stock.application.CandleCacheReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RedisCandleCacheReader implements CandleCacheReader {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<DomesticStockPriceResponse> getCandles(String key) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) return Optional.empty();

        try {
            DomesticStockPriceResponse domesticStockPriceResponse = objectMapper.readValue(json, DomesticStockPriceResponse.class);
            return Optional.of(domesticStockPriceResponse);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize redis cache. key=" + key, e);
        }
    }
}
