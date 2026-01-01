package com.hyunha.stock.stock.application;

import com.hyunha.stock.stock.infra.redis.dto.SymbolFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeRankingQueryService {

    private final TradeRankingCacheReader cacheReader;

    public List<SymbolFeature> getRanking() {
        return cacheReader.getRanking()
                .orElseThrow(() -> new IllegalStateException("Cache miss[getRanking]"));
    }
}