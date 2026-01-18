package com.hyunha.stock.stock.application;

import com.hyunha.stock.stock.api.dto.GetInvestmentOpinionResponse;
import com.hyunha.stock.stock.domain.port.out.StockCacheReader;
import com.hyunha.stock.stock.domain.port.out.StockCacheWriter;
import com.hyunha.stock.stock.domain.port.out.StockQueryPort;
import com.hyunha.stock.stock.infra.redis.RedisKeyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StockQueryService {

    private final StockCacheReader stockCacheReader;
    private final StockCacheWriter stockCacheWriter;
    private final StockQueryPort stockQueryPort;

    public List<GetInvestmentOpinionResponse> getInvestmentOpinion(String symbol) {
        return stockCacheReader.getInvestmentOpinion(symbol)
                .orElseGet(() -> {
                    List<GetInvestmentOpinionResponse> investmentOpinion = stockQueryPort.getInvestmentOpinion(symbol);
                    stockCacheWriter.save(RedisKeyFactory.INVESTMENT_OPINION_KEY, investmentOpinion);
                    return investmentOpinion;
                });
    }
}
