package com.hyunha.stock.stock.api;

import com.hyunha.stock.stock.api.dto.GetInvestmentOpinionResponse;
import com.hyunha.stock.stock.application.StockQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/api/stocks/v1")
@RestController
public class StockController {

    private final StockQueryService stockQueryService;

    @GetMapping("/investment-opinion")
    public List<GetInvestmentOpinionResponse> getInvestmentOpinion(@RequestParam String symbol) {
        return stockQueryService.getInvestmentOpinion(symbol);
    }
}
