package com.hyunha.stock.stock.api;

import com.hyunha.stock.stock.application.FluctuationQueryService;
import com.hyunha.stock.stock.infra.redis.dto.FluctuationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/stocks/v1")
@RestController
public class FluctuationController {

    private final FluctuationQueryService fluctuationQueryService;

    @GetMapping("/top-gainers")
    public List<FluctuationResponse.Output> getTopGainers() {
        return fluctuationQueryService.getTopGainers();
    }

    @GetMapping("/top-losers")
    public List<FluctuationResponse.Output> getTopLosers() {
        return fluctuationQueryService.getTopLosers();
    }
}
