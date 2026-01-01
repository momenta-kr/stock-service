package com.hyunha.stock.stock.application;

import com.hyunha.stock.stock.infra.redis.dto.IndustryIndexPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IndustryIndexQueryService {

    private final IndustryIndexCacheReader industryIndexCacheReader;

    public List<IndustryIndexPriceResponse.IndustryIndexItem> getIndustryIndexPrice() {
        return industryIndexCacheReader.getIndustryIndexPrice().orElseThrow();
    }
}
