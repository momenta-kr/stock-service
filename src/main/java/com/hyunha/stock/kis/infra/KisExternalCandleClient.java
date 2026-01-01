package com.hyunha.stock.kis.infra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hyunha.stock.kis.application.ExternalCandleClient;
import com.hyunha.stock.kis.infra.dto.Candle;
import com.hyunha.stock.kis.infra.dto.CandlesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KisExternalCandleClient implements ExternalCandleClient {

  private static final String TR_ID = "HHDFS76950200";
  private static final String PATH = "/uapi/overseas-price/v1/quotations/inquire-time-itemchartprice";

  private final WebClient kisWebClient;          // baseUrl = https://openapi.koreainvestment.com:9443
  private final TokenProvider tokenProvider;

  @Value("${kis.appkey}")
  private String appKey;

  @Value("${kis.appsecret}")
  private String appSecret;

  @Override
  public CandlesResponse fetchCandles(String excd, String symbol, int nmin, int limit) {
    // 문서상 NREC 최대 120
    int nrec = Math.min(Math.max(limit, 1), 120);

    KisCandleResponse res;
    try {
      res = kisWebClient.get()
          .uri(uriBuilder -> uriBuilder
              .path(PATH)
              .queryParam("AUTH", "")          // 문서: "" 공백
              .queryParam("EXCD", excd)        // NYS/NAS/AMS/HKS...
              .queryParam("SYMB", symbol)      // TSLA/AAPL...
              .queryParam("NMIN", nmin)        // 1=1분,2=2분...
              .queryParam("PINC", "0")         // 0=당일, 1=전일포함 (초기엔 0 권장)
              .queryParam("NEXT", "")          // 처음조회: "" / 다음조회: "1"
              .queryParam("NREC", String.valueOf(nrec))
              .queryParam("FILL", "")          // "" 공백
              .queryParam("KEYB", "")          // 처음조회: "" (다음조회용 키는 필요 시 확장)
              .build())
          .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenProvider.getAccessToken())
          .header("appkey", appKey)
          .header("appsecret", appSecret)
          .header("tr_id", TR_ID)
          .retrieve()
          .bodyToMono(KisCandleResponse.class)
          .block(Duration.ofSeconds(4));
    } catch (WebClientResponseException e) {
      throw new RuntimeException("KIS candle API HTTP error: status=" + e.getStatusCode() + " body=" + e.getResponseBodyAsString(), e);
    } catch (Exception e) {
      throw new RuntimeException("KIS candle API error", e);
    }

    if (res == null) {
      throw new RuntimeException("KIS candle API empty response");
    }
    if (!"0".equals(res.rt_cd)) {
      throw new RuntimeException("KIS candle API failed: rt_cd=" + res.rt_cd + " msg_cd=" + res.msg_cd + " msg1=" + res.msg1);
    }

    // output2 -> 우리 서비스 캔들로 변환
    List<Candle> candles = new ArrayList<>();
    if (res.output2 != null) {
      for (KisCandleRow r : res.output2) {
        candles.add(toCandle(r));
      }
    }

    return new CandlesResponse(excd, symbol, nmin, candles);
  }

  private Candle toCandle(KisCandleRow r) {
    // 문서: kymd(YYYYMMDD) + khms(HHMMSS) 가 “한국기준”
    LocalDate date = LocalDate.parse(r.kymd, DateTimeFormatter.BASIC_ISO_DATE);
    LocalTime time = LocalTime.parse(r.khms, DateTimeFormatter.ofPattern("HHmmss"));
    LocalDateTime kstDateTime = LocalDateTime.of(date, time);

    return new Candle(
        kstDateTime,
        bd(r.open),
        bd(r.high),
        bd(r.low),
        bd(r.last),
        lng(r.evol),
        lng(r.eamt)
    );
  }

  private BigDecimal bd(String s) {
    if (s == null || s.isBlank()) return BigDecimal.ZERO;
    return new BigDecimal(s.trim());
  }

  private long lng(String s) {
    if (s == null || s.isBlank()) return 0L;
    // 일부 값이 소수점으로 올 가능성 대비
    return new BigDecimal(s.trim()).longValue();
  }

  // ======= KIS 응답 DTO (문서 기반) =======
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class KisCandleResponse {
    public String rt_cd;     // 성공실패 여부
    public String msg_cd;    // 응답코드
    public String msg1;      // 응답메세지
    public List<KisOutput1> output1;  // object array
    public List<KisCandleRow> output2; // array
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class KisOutput1 {
    public String rsym; // 실시간종목코드
    public String zdiv;
    public String stim;
    public String etim;
    public String sktm;
    public String ektm;
    public String next;
    public String more;
    public String nrec;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class KisCandleRow {
    public String tymd; // 현지영업일자
    public String xymd; // 현지기준일자
    public String xhms; // 현지기준시간
    public String kymd; // 한국기준일자
    public String khms; // 한국기준시간
    public String open; // 시가
    public String high; // 고가
    public String low;  // 저가
    public String last; // 종가
    public String evol; // 체결량
    public String eamt; // 체결대금
  }
}
