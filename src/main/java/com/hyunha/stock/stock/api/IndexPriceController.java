package com.hyunha.stock.stock.api;

import com.hyunha.stock.stock.application.IndexPriceQueryService;
import com.hyunha.stock.stock.infra.redis.dto.IndexPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/stocks/v1/index-price")
@RestController
public class IndexPriceController {

    private final IndexPriceQueryService indexPriceQueryService;

    @GetMapping
    public IndexPriceResponse.Output getIndexPrice() {
        return indexPriceQueryService.getIndexPrice();
    }

}
