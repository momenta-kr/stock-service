package com.hyunha.stock.stock.domain.port.out;

import com.hyunha.stock.stock.api.dto.GetRealTimeNewsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RealTimeNewsQueryPort {

    Slice<GetRealTimeNewsResponse> getRealTimeNews(Pageable pageable, String timeRange, String sentiment, String category);
}
