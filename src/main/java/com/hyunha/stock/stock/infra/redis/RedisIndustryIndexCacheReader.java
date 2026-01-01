package com.hyunha.stock.stock.infra.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunha.stock.stock.application.IndustryIndexCacheReader;
import com.hyunha.stock.stock.infra.redis.dto.IndustryIndexPriceResponse;
import com.hyunha.stock.stock.infra.redis.dto.SymbolFeature;
import com.hyunha.stock.stock.infra.redis.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RedisIndustryIndexCacheReader implements IndustryIndexCacheReader {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<List<IndustryIndexPriceResponse.IndustryIndexItem>> getIndustryIndexPrice() {
        String key = RedisKey.INDUSTRY_INDEX_PRICE.getKey();
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) return Optional.empty();

        try {
            IndustryIndexPriceResponse industryIndexPriceResponse = objectMapper.readValue(json, IndustryIndexPriceResponse.class);
            return Optional.of(industryIndexPriceResponse.getIndustryIndexes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize redis cache. key=" + key, e);
        }
    }
}
