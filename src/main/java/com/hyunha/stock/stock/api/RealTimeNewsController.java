package com.hyunha.stock.stock.api;

import com.hyunha.stock.stock.api.dto.GetRealTimeNewsResponse;
import com.hyunha.stock.stock.application.RealTimeNewsQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/stocks/v1/news")
@RestController
public class RealTimeNewsController {

    private final RealTimeNewsQueryService realTimeNewsQueryService;

    @GetMapping
    public Slice<GetRealTimeNewsResponse> getRealTimeNews(
            Pageable pageable,
            String timeRange,
            String sentiment,
            String category
    ) {
        return realTimeNewsQueryService.getRealTimeNews(pageable, timeRange, sentiment, category);
    }
}
