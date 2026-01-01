package com.hyunha.stock.stock.domain.cache;

public record CachedPayload<T>(long fetchedAtEpochMs, T data) {
}
