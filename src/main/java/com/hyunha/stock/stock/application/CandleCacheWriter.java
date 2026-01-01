package com.hyunha.stock.stock.application;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CandleCacheWriter {
    void save(String key, Object value) throws JsonProcessingException;
}
