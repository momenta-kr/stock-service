package com.hyunha.stock.stock.api;

import com.hyunha.stock.stock.application.IndustryIndexQueryService;
import com.hyunha.stock.stock.infra.redis.dto.IndustryIndexPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/stocks/v1")
@RestController
public class IndustryIndexController {

    private final IndustryIndexQueryService industryIndexQueryService;

    @GetMapping("/industry-index")
    public List<IndustryIndexPriceResponse.IndustryIndexItem> getIndustryIndex() {
        return industryIndexQueryService.getIndustryIndexPrice();
    }
}
