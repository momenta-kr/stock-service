package com.hyunha.stock.stock.api.dto;

import java.util.List;

public record AiNewsFeedResponse(
        List<AiNewsItemDto> items,
        String nextCursor
) {
    public record AiNewsItemDto(
            String id,
            String title,
            String safeBrief,
            String sentiment,
            String category,
            String publishedAt,
            String source,
            String domain,
            String url
    ) {}
}
