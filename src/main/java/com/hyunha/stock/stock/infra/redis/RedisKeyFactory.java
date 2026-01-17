package com.hyunha.stock.stock.infra.redis;

import com.hyunha.stock.stock.domain.enums.TradeRankingType;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyFactory {
    public static final String RANKING_KEY = "stock:momentum:top20:NAS";
    public static final String INVESTMENT_OPINION_KEY = "stock:investment-opinion";


}