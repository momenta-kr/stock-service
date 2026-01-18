package com.hyunha.stock.stock.infra.redis;

import com.hyunha.stock.stock.api.dto.GetInvestmentOpinionResponse;
import com.hyunha.stock.stock.domain.port.out.StockCacheWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RedisStockCacheWriter implements StockCacheWriter {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(String key, List<GetInvestmentOpinionResponse> investmentOpinion) {
        stringRedisTemplate.opsForValue().set(key, investmentOpinion.toString());
    }
}
