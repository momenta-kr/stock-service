package com.hyunha.stock.stock.api;

import com.hyunha.stock.stock.api.dto.AiNewsFeedResponse;
import com.hyunha.stock.stock.api.dto.AiNewsInsightsResponse;
import com.hyunha.stock.stock.api.dto.AiNewsOverviewResponse;
import com.hyunha.stock.stock.application.StockAiNewsQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks/v1/ai-news/{stockCode}")
public class StockAiNewsController {

    private final StockAiNewsQueryService service;

    @GetMapping("/overview")
    public AiNewsOverviewResponse overview(
            @PathVariable String stockCode,
            @RequestParam(required = false) String fromIso,
            @RequestParam(required = false) String toIso,
            @RequestParam(required = false) String sentiment,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "20") int size
    ) {
        return service.getOverview(stockCode, fromIso, toIso, sentiment, category, size);
    }

    @GetMapping
    public AiNewsFeedResponse feed(
            @PathVariable String stockCode,
            @RequestParam(required = false) String fromIso,
            @RequestParam(required = false) String toIso,
            @RequestParam(required = false) String sentiment,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String cursor
    ) {
        return service.getFeed(stockCode, fromIso, toIso, sentiment, category, size, cursor);
    }

    @GetMapping("/insights")
    public AiNewsInsightsResponse insights(
            @PathVariable String stockCode,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return service.getInsights(stockCode, from, to);
    }
}
