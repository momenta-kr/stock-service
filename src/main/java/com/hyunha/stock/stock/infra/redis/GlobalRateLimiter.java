package com.hyunha.stock.stock.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class GlobalRateLimiter {

  private final StringRedisTemplate redis;

  public boolean tryAcquire(String name, int limitPerSec) {
    long sec = System.currentTimeMillis() / 1000;
    String key = "ratelimit:" + name + ":" + sec;

    Long count = redis.opsForValue().increment(key);

    if (count != null && count == 1L) {
      redis.expire(key, Duration.ofSeconds(2));
    }

    return count != null && count <= limitPerSec;
  }
}
