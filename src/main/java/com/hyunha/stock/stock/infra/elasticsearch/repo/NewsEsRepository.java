package com.hyunha.stock.stock.infra.elasticsearch.repo;

import com.hyunha.stock.stock.infra.elasticsearch.document.NewsDocument;
import com.hyunha.stock.stock.infra.elasticsearch.repo.custom.NewsEsRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface NewsEsRepository extends ElasticsearchRepository<NewsDocument, String>, NewsEsRepositoryCustom {
    @Override
    Slice<NewsDocument> findRealtime(String timeRange, String sentiment, String category, Pageable pageable);

}
