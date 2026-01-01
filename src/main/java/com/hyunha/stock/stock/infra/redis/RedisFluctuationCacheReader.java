package com.hyunha.stock.stock.infra.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunha.stock.stock.application.FluctuationCacheReader;
import com.hyunha.stock.stock.infra.redis.dto.FluctuationResponse;
import com.hyunha.stock.stock.infra.redis.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RedisFluctuationCacheReader implements FluctuationCacheReader {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<List<FluctuationResponse.Output>> getTopGainers() {
        String key = RedisKey.TOP_GAINERS.getKey();
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) return Optional.empty();

        try {
            FluctuationResponse fluctuationResponse = objectMapper.readValue(json, FluctuationResponse.class);
            return Optional.of(fluctuationResponse.getOutput());
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize redis cache. key=" + key, e);
        }
    }


    @Override
    public Optional<List<FluctuationResponse.Output>> getTopLosers() {
        String key = RedisKey.TOP_LOSERS.getKey();
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) return Optional.empty();

        try {
            FluctuationResponse fluctuationResponse = objectMapper.readValue(json, FluctuationResponse.class);
            return Optional.of(fluctuationResponse.getOutput());
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize redis cache. key=" + key, e);
        }
    }
}
