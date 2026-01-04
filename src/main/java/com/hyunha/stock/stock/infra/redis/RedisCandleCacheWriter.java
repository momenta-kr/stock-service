package com.hyunha.stock.stock.infra.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunha.stock.stock.application.CandleCacheWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class RedisCandleCacheWriter implements CandleCacheWriter {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void save(String key, Object value) throws JsonProcessingException {
        System.out.println("RedisCandleCacheWriter save: " + key + " :" + value);
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value), Duration.ofDays(365));
    }
}
