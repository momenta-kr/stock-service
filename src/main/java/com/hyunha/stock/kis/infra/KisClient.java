package com.hyunha.stock.kis.infra;

import com.hyunha.stock.kis.infra.dto.DomesticStockPriceResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class KisClient {

    private final RestClient kisRestClient;
    private final TokenProvider tokenProvider;
    private final KisProperties kisProperties;

    /**
     * https://apiportal.koreainvestment.com/apiservice-apiservice?/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice
     * 국내주식기간별시세(일/주/월/년)[v1_국내주식-016]
     */
    public DomesticStockPriceResponse fetchDomesticStockPeriodPrices(String symbol, String period, String from, String to) {
        return kisRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice")
                        .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                        .queryParam("FID_INPUT_ISCD", symbol)
                        .queryParam("FID_INPUT_DATE_1", from)
                        .queryParam("FID_INPUT_DATE_2", to)
                        .queryParam("FID_PERIOD_DIV_CODE", period)
                        .queryParam("FID_ORG_ADJ_PRC", "0")
                        .build())
                .headers(getCommonHttpHeaders("FHKST03010100"))
                .retrieve()
                .body(DomesticStockPriceResponse.class);
    }

    private @NonNull Consumer<HttpHeaders> getCommonHttpHeaders(String transactionId) {
        return httpHeaders -> {
            httpHeaders.setBearerAuth(tokenProvider.getAccessToken());
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("appkey", kisProperties.appKey());
            httpHeaders.set("appsecret", kisProperties.appSecret());
            httpHeaders.set("tr_id", transactionId);
            httpHeaders.set("custtype", "P");
        };
    }
}
