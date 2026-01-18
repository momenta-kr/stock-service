package com.hyunha.stock.stock.infra;

import com.hyunha.stock.kis.infra.KisClient;
import com.hyunha.stock.kis.infra.dto.InvestmentOpinionApiResponse;
import com.hyunha.stock.stock.api.dto.GetInvestmentOpinionResponse;
import com.hyunha.stock.stock.domain.port.out.StockQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class StockQueryAdapter implements StockQueryPort {

    private final KisClient kisClient;

    @Override
    public List<GetInvestmentOpinionResponse> getInvestmentOpinion(String symbol) {
        InvestmentOpinionApiResponse response = kisClient.fetchInvestmentOpinion(symbol, LocalDateTime.now());
        return GetInvestmentOpinionResponse.from(response.getItems());
    }
}
