package com.hyunha.stock.stock.infra.elasticsearch.repo.custom;

import com.hyunha.stock.stock.infra.elasticsearch.document.NewsDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NewsEsRepositoryCustom {
    Slice<NewsDocument> findRealtime(String timeRange, String sentiment, String category, Pageable pageable);
}
