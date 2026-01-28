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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component
public class RealTimeNewsQueryAdapter implements RealTimeNewsQueryPort {

    private static final int PAGE_SIZE = 500;

    private final NewsEsRepository newsEsRepository;
    private final StockRepository stockRepository;

    public List<GetRealTimeNewsResponse> getRealTimeNews() {
        List<NewsDocument> newsDocuments =
                newsEsRepository.findByOrderByPublishedAtDesc(Pageable.ofSize(PAGE_SIZE));

        if (newsDocuments.isEmpty()) {
            return List.of();
        }

        // 1) 뉴스 문서에서 심볼(대표 + related) 뽑고, kospi/kosdaq 둘 다 만들어서 중복 제거
        Set<StockMasterId> stockMasterIds = newsDocuments.stream()
                .flatMap(RealTimeNewsQueryAdapter::extractSymbols)
                .filter(StringUtils::isNotBlank)
                .flatMap(RealTimeNewsQueryAdapter::toBothMarkets)
                .collect(Collectors.toCollection(LinkedHashSet::new)); // 순서 유지 + 중복 제거

        // 2) DB 조회 후 symbol 기준으로 매핑(중복은 먼저 들어온 걸 우선)
        Map<String, Stock> stockByStockCode = stockRepository.findByIdIn(new ArrayList<>(stockMasterIds))
                .stream()
                .collect(Collectors.toMap(
                        stock -> stock.getId().getSymbol(),
                        Function.identity(),
                        (a, b) -> a
                ));

        // 3) 응답 변환
        return newsDocuments.stream()
                .map(doc -> GetRealTimeNewsResponse.fromDocument(doc, stockByStockCode))
                .toList();
    }

    private static Stream<String> extractSymbols(NewsDocument doc) {
        Stream<String> primary = Stream.of(doc.getSymbol());
        Stream<String> related = (doc.getRelatedStockCodes() == null)
                ? Stream.empty()
                : doc.getRelatedStockCodes().stream();
        return Stream.concat(primary, related);
    }

    private static Stream<StockMasterId> toBothMarkets(String symbol) {
        // 필요하면 여기서 symbol 포맷 검증(예: 6자리)도 추가 가능
        return Stream.of(
                StockMasterId.kospi(symbol),
                StockMasterId.kosdaq(symbol)
        );
    }
}
