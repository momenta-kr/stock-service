package com.hyunha.stock.stock.domain.port.out;

import com.hyunha.stock.kis.infra.dto.InvestmentOpinionApiResponse;
import com.hyunha.stock.stock.api.dto.GetInvestmentOpinionResponse;

import java.util.List;

public interface StockQueryPort {
    List<GetInvestmentOpinionResponse> getInvestmentOpinion(String symbol);
}
