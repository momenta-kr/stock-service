package com.hyunha.stock.stock.infra.redis;

import com.hyunha.stock.stock.domain.enums.TradeRankingType;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyFactory {
    private static final String LATEST_KEY = "trade:rank:NAS:%s:latest";
    public static final String RANKING_KEY = "stock:momentum:top20:NAS";

    public String latestKey(TradeRankingType type) {
        return LATEST_KEY.formatted(type.name());
    }
}