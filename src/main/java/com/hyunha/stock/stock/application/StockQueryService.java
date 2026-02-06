package com.hyunha.stock.stock.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyunha.stock.stock.api.dto.GetDomesticStockCurrentPriceOutput;
import com.hyunha.stock.stock.api.dto.GetInvestmentOpinionResponse;
import com.hyunha.stock.stock.api.dto.GetVolumeRankingResponse;
import com.hyunha.stock.stock.domain.port.out.StockCacheReader;
import com.hyunha.stock.stock.domain.port.out.StockCacheWriter;
import com.hyunha.stock.stock.domain.port.out.StockQueryPort;
import com.hyunha.stock.stock.infra.redis.RedisKeyFactory;
import com.hyunha.stock.stock.infra.redis.dto.DomesticStockCurrentPriceResponse;
import com.hyunha.stock.stock.infra.redis.dto.VolumeRankResponse;
import com.hyunha.stock.stock.infra.redis.enums.RedisKey;
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

    public GetDomesticStockCurrentPriceOutput getStock(String stockCode) {
        List<DomesticStockCurrentPriceResponse> domesticStockCurrentPrices = stockCacheReader.getDomesticStockCurrentPrices(List.of(RedisKey.STOCK_INFO.getKey() + ":" + stockCode));
        if (domesticStockCurrentPrices.isEmpty()) return null;

        return GetDomesticStockCurrentPriceOutput.from(domesticStockCurrentPrices.getFirst().getOutput());
    }

    public List<GetVolumeRankingResponse> getVolumeRankings() throws JsonProcessingException {
        VolumeRankResponse volumeRanking = stockCacheReader.getVolumeRanking();

        if (volumeRanking == null) return List.of();

        return volumeRanking.getOutputList().stream()
                .map(output -> new GetVolumeRankingResponse(
                        output.getShortSymbolCode(),
                        output.getHtsKoreanName(),
                        output.getDataRank(),
                        output.getCurrentPrice(),
                        output.getPrevDayDiff(),
                        output.getPrevDayChangeRate()
                ))
                .toList();
    }
}
