package com.hyunha.stock.stock.application;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.json.JsonData;
import com.hyunha.stock.stock.api.dto.AiNewsFeedResponse;
import com.hyunha.stock.stock.api.dto.AiNewsInsightsResponse;
import com.hyunha.stock.stock.api.dto.AiNewsOverviewResponse;
import com.hyunha.stock.stock.infra.elasticsearch.document.NewsDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockAiNewsQueryService {

    private final ElasticsearchOperations operations;

    // ✅ ES 필드명(매핑에서 name으로 지정한 것 포함)
    private static final String F_PUBLISHED_AT = "published_at";
    private static final String F_SENTIMENT = "sentiment";
    private static final String F_CATEGORY = "category";
    private static final String F_DOMAIN = "domain";
    private static final String F_SOURCE = "source";
    private static final String F_URL = "url";
    private static final String F_ID = "id";

    private static final String F_RELATED_STOCK_CODES_KW = "relatedStockCodes.keyword";
    private static final String F_SYMBOL = "symbol";

    public AiNewsOverviewResponse getOverview(String stockCode, String fromIso, String toIso, String sentiment, String category, int size) {
        var insights = getInsights(stockCode, fromIso, toIso);
        var feed = getFeed(stockCode, fromIso, toIso, sentiment, category, size, null);
        return new AiNewsOverviewResponse(insights, feed);
    }
    public AiNewsFeedResponse getFeed(
            String stockCode,
            String fromIso,
            String toIso,
            String sentiment,
            String category,
            int size,
            String cursor
    ) {
        int pageSize = Math.max(1, Math.min(size <= 0 ? 20 : size, 50));
        int fetchSize = pageSize + 1; // ✅ hasNext 판별용

        Query query = buildBaseBoolQuery(stockCode, fromIso, toIso, sentiment, category);

        NativeQueryBuilder qb = new NativeQueryBuilder()
                .withQuery(query)
                .withPageable(PageRequest.of(0, fetchSize))
                .withTrackTotalHits(false)
                // ✅ date sort는 format 고정 추천 (가능하면 epoch_millis)
                .withSort(s -> s.field(f -> f.field(F_PUBLISHED_AT).order(SortOrder.Desc).format("epoch_millis")))
                .withSort(s -> s.field(f -> f.field(F_ID).order(SortOrder.Desc)));

        // ✅ search_after cursor 적용 (타입 보존 중요)
        if (StringUtils.hasText(cursor)) {
            Object[] sa = SearchAfterCursorCodec.decode(cursor); // [publishedAt, id]
            qb.withSearchAfter(Arrays.asList(sa));
        }

        SearchHits<NewsDocument> hits = operations.search(qb.build(), NewsDocument.class);

        var searchHits = hits.getSearchHits();
        boolean hasNext = searchHits.size() > pageSize;

        // ✅ pageSize만큼만 응답 아이템으로 사용
        var pageHits = hasNext ? searchHits.subList(0, pageSize) : searchHits;

        List<AiNewsFeedResponse.AiNewsItemDto> items = pageHits.stream()
                .map(this::toItem)
                .toList();

        // ✅ hasNext일 때만 nextCursor 생성
        String nextCursor = null;
        if (hasNext && !pageHits.isEmpty()) {
            SearchHit<NewsDocument> last = pageHits.get(pageHits.size() - 1);
            Object[] sortValues = last.getSortValues().toArray();
            nextCursor = SearchAfterCursorCodec.encode(sortValues);
        }

        return new AiNewsFeedResponse(items, nextCursor);
    }

    public AiNewsInsightsResponse getInsights(String stockCode, String fromIso, String toIso) {

        Query query = buildBaseBoolQuery(stockCode, fromIso, toIso, null,  null);

        NativeQueryBuilder qb = new NativeQueryBuilder()
                .withQuery(query)
                .withTrackTotalHits(true)
                // ✅ PageRequest size는 1 이상만 가능
                .withPageable(PageRequest.of(0, 1))
                // ✅ 대신 실제 검색 결과는 0개만 가져오게 해서 "size=0" 효과
                .withMaxResults(0);

        qb.withAggregation("sentiments",
                new Aggregation.Builder().terms(t -> t.field(F_SENTIMENT).size(10)).build()
        );
        qb.withAggregation("categories",
                new Aggregation.Builder().terms(t -> t.field(F_CATEGORY).size(10)).build()
        );
        qb.withAggregation("domains",
                new Aggregation.Builder().terms(t -> t.field(F_DOMAIN).size(10)).build()
        );

        NativeQuery nativeQuery = qb.build();

        SearchHits<NewsDocument> hits = operations.search(nativeQuery, NewsDocument.class);

        long total = hits.getTotalHits();

        List<AiNewsInsightsResponse.KeyCountDto> sentiments = readTermsAgg(hits, "sentiments");
        List<AiNewsInsightsResponse.KeyCountDto> topCategories = readTermsAgg(hits, "categories");
        List<AiNewsInsightsResponse.KeyCountDto> topDomains = readTermsAgg(hits, "domains");

        return new AiNewsInsightsResponse(total, sentiments, topCategories, topDomains);
    }


    private AiNewsFeedResponse.AiNewsItemDto toItem(SearchHit<NewsDocument> hit) {
        NewsDocument d = hit.getContent();
        return new AiNewsFeedResponse.AiNewsItemDto(
                d.getId(),
                d.getTitle(),
                d.getSafeBrief(),
                d.getSentiment(),
                d.getCategory(),
                d.getPublishedAt(),
                d.getSource(),
                d.getDomain(),
                d.getUrl()
        );
    }

    /**
     * ✅ 공통 필터:
     * - stockCode 매칭: (symbol == stockCode) OR (relatedStockCodes.keyword contains stockCode)
     * - date range: published_at
     * - optional: sentiment, category
     * - optional: q (multi_match: title, safeBrief, description)
     */

    private Query buildBaseBoolQuery(
            String stockCode,
            String fromIso,
            String toIso,
            String sentiment,
            String category
    ) {
        BoolQuery.Builder bool = new BoolQuery.Builder();

        // ✅ 종목 필터: should + minimum_should_match=1
        BoolQuery.Builder stockBool = new BoolQuery.Builder()
                .should(s -> s.term(t -> t.field(F_SYMBOL).value(stockCode)))
                .should(s -> s.term(t -> t.field(F_RELATED_STOCK_CODES_KW).value(stockCode)))
                .minimumShouldMatch("1");

        bool.filter(f -> f.bool(stockBool.build()));

        // ✅ 날짜 범위 (published_at: date)
        if (StringUtils.hasText(fromIso) || StringUtils.hasText(toIso)) {
            bool.filter(f -> f.range(r -> r.date(d -> {
                d.field(F_PUBLISHED_AT);
                if (StringUtils.hasText(fromIso)) d.gte(JsonData.of(fromIso).toString());
                if (StringUtils.hasText(toIso)) d.lte(JsonData.of(toIso).toString());
                return d;
            })));
        }

        // ✅ sentiment, category 필터
        if (StringUtils.hasText(sentiment)) {
            bool.filter(f -> f.term(t -> t.field(F_SENTIMENT).value(sentiment)));
        }
        if (StringUtils.hasText(category)) {
            bool.filter(f -> f.term(t -> t.field(F_CATEGORY).value(category)));
        }

        return Query.of(qb -> qb.bool(bool.build()));
    }

    private List<AiNewsInsightsResponse.KeyCountDto> readTermsAgg(SearchHits<NewsDocument> hits, String aggName) {
        if (hits.getAggregations() == null) return List.of();

        if (!(hits.getAggregations() instanceof ElasticsearchAggregations ea)) return List.of();

        // ✅ 버전 차이 덜 타게 Map으로 꺼내기
        ElasticsearchAggregation agg = ea.aggregationsAsMap().get(aggName);
        if (agg == null) return List.of();

        var aggregate = agg.aggregation().getAggregate();

        // keyword terms는 보통 sterms로 들어옴
        if (!aggregate.isSterms()) return List.of();

        return aggregate.sterms().buckets().array().stream()
                .map(b -> new AiNewsInsightsResponse.KeyCountDto(b.key().stringValue(), b.docCount()))
                .toList();
    }

}
