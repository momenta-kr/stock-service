package com.hyunha.stock.stock.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class SearchAfterCursorCodec {
    private static final ObjectMapper om = new ObjectMapper();

    private SearchAfterCursorCodec() {}

    public static String encode(Object[] sortValues) {
        try {
            // sortValues: [publishedAtMillis(long), id(String)]
            byte[] json = om.writeValueAsBytes(sortValues);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(json); // ✅ URL-safe
        } catch (Exception e) {
            throw new IllegalArgumentException("encode cursor failed", e);
        }
    }

    public static Object[] decode(String cursor) {
        if (cursor == null || cursor.isBlank()) return new Object[0];
        try {
            // ✅ 혹시라도 querystring에서 +가 공백으로 바뀐 경우를 “임시 복구”
            // (근본해결은 encode를 url-safe로 바꾸는 것)
            String fixed = cursor.trim().replace(" ", "+");

            byte[] json = Base64.getUrlDecoder().decode(fixed);
            JsonNode arr = om.readTree(json);

            long publishedAtMillis = arr.get(0).asLong();
            String id = arr.get(1).asText();

            return new Object[]{ publishedAtMillis, id };
        } catch (Exception e) {
            throw new IllegalArgumentException("decode cursor failed. cursor=" + cursor, e);
        }
    }
}
