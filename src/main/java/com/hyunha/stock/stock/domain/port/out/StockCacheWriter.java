package com.hyunha.stock.stock.domain.port.out;

import com.hyunha.stock.stock.api.dto.GetInvestmentOpinionResponse;

import java.util.List;

public interface StockCacheWriter {
    void save(String key, List<GetInvestmentOpinionResponse> investmentOpinion);
}
