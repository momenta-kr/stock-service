package com.hyunha.stock.stock.infra.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunha.stock.stock.application.FluctuationCacheReader;
import com.hyunha.stock.stock.application.IndexPriceCacheReader;
import com.hyunha.stock.stock.infra.redis.dto.FluctuationResponse;
import com.hyunha.stock.stock.infra.redis.dto.IndexPriceResponse;
import com.hyunha.stock.stock.infra.redis.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RedisIndexPriceCacheReader implements IndexPriceCacheReader {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<IndexPriceResponse.Output> getIndexPrice() {
        String key = RedisKey.INDEX_PRICE.getKey();
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) return Optional.empty();

        try {
            IndexPriceResponse indexPriceResponse = objectMapper.readValue(json, IndexPriceResponse.class);
            return Optional.of(indexPriceResponse.getOutput());
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize redis cache. key=" + key, e);
        }
    }

}
