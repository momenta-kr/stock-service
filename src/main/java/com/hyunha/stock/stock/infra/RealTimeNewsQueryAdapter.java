package com.hyunha.stock.stock.infra;

import com.hyunha.stock.stock.api.dto.GetRealTimeNewsResponse;
import com.hyunha.stock.stock.domain.port.out.RealTimeNewsQueryPort;
import com.hyunha.stock.stock.infra.elasticsearch.document.NewsDocument;
import com.hyunha.stock.stock.infra.elasticsearch.repo.NewsEsRepository;
import com.hyunha.stock.stock.infra.jpa.entity.Stock;
import com.hyunha.stock.stock.infra.jpa.entity.StockMasterId;
import com.hyunha.stock.stock.infra.jpa.repo.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RealTimeNewsQueryAdapter implements RealTimeNewsQueryPort {

    private final NewsEsRepository newsEsRepository;
    private final StockRepository stockRepository;

    public List<GetRealTimeNewsResponse> getRealTimeNews() {
        List<NewsDocument> newsDocuments = newsEsRepository.findByOrderByPublishedAt(Pageable.ofSize(2000));
        List<StockMasterId> stockMasterIds = new ArrayList<>(newsDocuments.stream()
                .map(NewsDocument::getSymbol)
                .filter(StringUtils::isNotBlank)
                .map(StockMasterId::kospi)
                .toList());

        newsDocuments.stream()
                .map(NewsDocument::getSymbol)
                .filter(StringUtils::isNotBlank)
                .map(StockMasterId::kosdaq)
                .forEach(stockMasterIds::add);

        newsDocuments.stream()
                .filter(newsDocument -> newsDocument.getRelatedTickers() != null)
                .map(newsDocument -> {
                    List<StockMasterId> stockMasterIds1 = newsDocument.getRelatedTickers().stream().map(StockMasterId::kospi).toList();
                    List<StockMasterId> stockMasterIds2 = newsDocument.getRelatedTickers().stream().map(StockMasterId::kosdaq).toList();
                    return List.of(stockMasterIds1, stockMasterIds2);
                })
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .forEach(stockMasterIds::add);


        Map<String, Stock> stockByStockCode = stockRepository.findByIdIn(stockMasterIds)
                .stream()
                .collect(Collectors.toMap(stock -> stock.getId().getSymbol(),
                        Function.identity(),
                        (a, b) -> a));

        return newsDocuments.stream()
                .map(newsDocument -> GetRealTimeNewsResponse.fromDocument(newsDocument, stockByStockCode))
                .toList();
    }


}
