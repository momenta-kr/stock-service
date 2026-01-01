package com.hyunha.stock.stock.application;

import com.hyunha.stock.stock.infra.redis.dto.FluctuationResponse;

import java.util.List;
import java.util.Optional;

public interface FluctuationCacheReader {

    Optional<List<FluctuationResponse.Output>> getTopGainers();
    Optional<List<FluctuationResponse.Output>> getTopLosers();
}
