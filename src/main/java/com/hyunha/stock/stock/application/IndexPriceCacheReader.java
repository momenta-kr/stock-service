package com.hyunha.stock.stock.application;

import com.hyunha.stock.stock.infra.redis.dto.IndexPriceResponse;

import java.util.List;
import java.util.Optional;

public interface IndexPriceCacheReader {
    Optional<IndexPriceResponse.Output> getIndexPrice();
}
