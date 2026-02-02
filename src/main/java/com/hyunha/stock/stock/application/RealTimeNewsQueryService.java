package com.hyunha.stock.stock.application;

import com.hyunha.stock.stock.api.dto.GetRealTimeNewsResponse;
import com.hyunha.stock.stock.domain.port.out.RealTimeNewsQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RealTimeNewsQueryService {

    private final RealTimeNewsQueryPort realTimeNewsQueryPort;

    public Slice<GetRealTimeNewsResponse> getRealTimeNews(Pageable pageable, String timeRange, String sentiment, String category) {
        return realTimeNewsQueryPort.getRealTimeNews(pageable, timeRange, sentiment, category);
    }



}
