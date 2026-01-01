package com.hyunha.stock.stock.infra.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunha.stock.stock.application.TradeRankingCacheReader;
import com.hyunha.stock.stock.infra.redis.dto.SymbolFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisTradeRankingCacheReader implements TradeRankingCacheReader {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<List<SymbolFeature>> getRanking() {
        String key = RedisKeyFactory.RANKING_KEY;
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) return Optional.empty();

        try {
            return Optional.of(List.of(objectMapper.readValue(json, SymbolFeature[].class)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize redis cache. key=" + key, e);
        }
    }
}
