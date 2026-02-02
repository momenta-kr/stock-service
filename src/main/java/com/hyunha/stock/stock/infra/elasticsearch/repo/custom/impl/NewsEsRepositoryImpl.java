package com.hyunha.stock.stock.infra.elasticsearch.repo.custom.impl;

import com.hyunha.stock.stock.infra.elasticsearch.document.NewsDocument;
import com.hyunha.stock.stock.infra.elasticsearch.repo.custom.NewsEsRepositoryCustom;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RequiredArgsConstructor
public class NewsEsRepositoryImpl implements NewsEsRepositoryCustom {

    // 예: "1H", "6H", "1D", "7D", "30D", "ALL"
    private static final Pattern RANGE_PATTERN = Pattern.compile("^(\\d+)\\s*(MIN|M|H|D|W)$");

    private final ElasticsearchOperations operations;

    // ES 실제 필드명(매핑 name 사용)
    private static final String FIELD_PUBLISHED_AT = "published_at";
    private static final String FIELD_SENTIMENT = "sentiment";
    private static final String FIELD_CATEGORY = "category";


    @Override
    public Slice<NewsDocument> findRealtime(String timeRange, String sentiment, String category, Pageable pageable) {
        final Pageable safePageable = pageable != null ? pageable : PageRequest.of(0, 20);

        // ✅ Slice를 위해 size+1로 조회 후 hasNext 판단
        int size = safePageable.getPageSize();
        Pageable fetchPageable = PageRequest.of(safePageable.getPageNumber(), size + 1);

        Criteria criteria = new Criteria(); // match_all에 해당

        // sentiment 필터(옵션)
        if (StringUtils.isNotBlank(sentiment) && !"ALL".equalsIgnoreCase(sentiment)) {
            criteria = criteria.and(new Criteria(FIELD_SENTIMENT).is(sentiment));
        }

        // category 필터(옵션)
        if (StringUtils.isNotBlank(category) && !"ALL".equalsIgnoreCase(category)) {
            criteria = criteria.and(new Criteria(FIELD_CATEGORY).is(category));
        }

        // timeRange 필터(옵션) - published_at range
        Optional<Instant> fromOpt = parseTimeRange(timeRange);
        if (fromOpt.isPresent()) {
            Instant now = Instant.now();
            String fromIso = DateTimeFormatter.ISO_INSTANT.format(fromOpt.get());
            String toIso = DateTimeFormatter.ISO_INSTANT.format(now);

            criteria = criteria
                    .and(new Criteria(FIELD_PUBLISHED_AT).greaterThanEqual(fromIso))
                    .and(new Criteria(FIELD_PUBLISHED_AT).lessThanEqual(toIso));
        }

        CriteriaQuery query = new CriteriaQuery(criteria, fetchPageable);
        query.addSort(Sort.by(Sort.Order.desc(FIELD_PUBLISHED_AT))); // 최신순 고정

        SearchHits<NewsDocument> hits = operations.search(query, NewsDocument.class);

        List<NewsDocument> docs = hits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();

        boolean hasNext = docs.size() > size;
        if (hasNext) docs = docs.subList(0, size);

        return new SliceImpl<>(docs, safePageable, hasNext);
    }

    private static Optional<Instant> parseTimeRange(String timeRange) {
        if (StringUtils.isBlank(timeRange)) return Optional.empty();
        String s = timeRange.trim().toUpperCase(Locale.ROOT);

        if ("ALL".equals(s) || "0".equals(s)) return Optional.empty();

        // "24H" / "7D" / "30D" / "15MIN" 등
        Matcher m = RANGE_PATTERN.matcher(s);
        if (m.matches()) {
            long n = Long.parseLong(m.group(1));
            String unit = m.group(2);

            Duration d;
            switch (unit) {
                case "MIN":
                case "M":
                    d = Duration.ofMinutes(n);
                    break;
                case "H":
                    d = Duration.ofHours(n);
                    break;
                case "D":
                    d = Duration.ofDays(n);
                    break;
                case "W":
                    d = Duration.ofDays(n * 7);
                    break;
                default:
                    return Optional.empty();
            }
            return Optional.of(Instant.now().minus(d));
        }

        // 예상 못한 값이면 필터 적용 안 함(=ALL)
        return Optional.empty();
    }
}
