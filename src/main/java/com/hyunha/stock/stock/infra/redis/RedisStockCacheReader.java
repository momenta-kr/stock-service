package com.hyunha.stock.stock.infra.redis;

import com.hyunha.stock.stock.api.dto.GetInvestmentOpinionResponse;
import com.hyunha.stock.stock.domain.port.out.StockCacheReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RedisStockCacheReader implements StockCacheReader {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Optional<List<GetInvestmentOpinionResponse>> getInvestmentOpinion(String symbol) {


        return Optional.empty();
    }
}
