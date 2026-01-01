package com.hyunha.stock.kis.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * https://apiportal.koreainvestment.com/apiservice-apiservice?/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice
 * 국내주식기간별시세(일/주/월/년)[v1_국내주식-016]
 */
@Getter
@Setter
public class DomesticStockPriceResponse {

    /** 성공/실패 여부 */
    @JsonProperty("rt_cd")
    private String resultCode;

    /** 응답 코드 */
    @JsonProperty("msg_cd")
    private String messageCode;

    /** 응답 메시지 */
    @JsonProperty("msg1")
    private String message;

    /** 현재 시세 요약 */
    @JsonProperty("output1")
    private Snapshot snapshot;

    /** 기간별 시세 (차트용) */
    @JsonProperty("output2")
    private List<DailyPrice> prices;

    // =========================
    // output1 : 현재 시세 요약
    // =========================
    @Getter
    @Setter
    public static class Snapshot {

        @JsonProperty("prdy_vrss")
        private String changeFromPrevDay;          // 전일 대비

        @JsonProperty("prdy_vrss_sign")
        private String changeSign;                  // 전일 대비 부호

        @JsonProperty("prdy_ctrt")
        private String changeRate;                  // 전일 대비율

        @JsonProperty("stck_prdy_clpr")
        private String prevClosePrice;              // 전일 종가

        @JsonProperty("acml_vol")
        private String accumulatedVolume;           // 누적 거래량

        @JsonProperty("acml_tr_pbmn")
        private String accumulatedTradeAmount;      // 누적 거래대금

        @JsonProperty("hts_kor_isnm")
        private String stockName;                   // 종목명

        @JsonProperty("stck_prpr")
        private String currentPrice;                // 현재가

        @JsonProperty("stck_shrn_iscd")
        private String shortStockCode;              // 단축 종목 코드

        @JsonProperty("prdy_vol")
        private String prevDayVolume;               // 전일 거래량

        @JsonProperty("stck_mxpr")
        private String upperLimitPrice;             // 상한가

        @JsonProperty("stck_llam")
        private String lowerLimitPrice;             // 하한가

        @JsonProperty("stck_oprc")
        private String openPrice;                   // 시가

        @JsonProperty("stck_hgpr")
        private String highPrice;                   // 고가

        @JsonProperty("stck_lwpr")
        private String lowPrice;                    // 저가

        @JsonProperty("stck_prdy_oprc")
        private String prevDayOpenPrice;            // 전일 시가

        @JsonProperty("stck_prdy_hgpr")
        private String prevDayHighPrice;            // 전일 고가

        @JsonProperty("stck_prdy_lwpr")
        private String prevDayLowPrice;             // 전일 저가

        @JsonProperty("askp")
        private String askPrice;                    // 매도호가

        @JsonProperty("bidp")
        private String bidPrice;                    // 매수호가

        @JsonProperty("prdy_vrss_vol")
        private String changeVolumeFromPrevDay;     // 전일 대비 거래량

        @JsonProperty("vol_tnrt")
        private String turnoverRate;                // 거래량 회전율

        @JsonProperty("stck_fcam")
        private String faceValue;                   // 액면가

        @JsonProperty("lstn_stcn")
        private String listedShares;                // 상장 주식수

        @JsonProperty("cpfn")
        private String capitalAmount;               // 자본금

        @JsonProperty("hts_avls")
        private String marketCap;                   // 시가총액

        @JsonProperty("per")
        private String per;                         // PER

        @JsonProperty("eps")
        private String eps;                         // EPS

        @JsonProperty("pbr")
        private String pbr;                         // PBR

        @JsonProperty("itewhol_loan_rmnd_ratem")
        private String marginLoanRate;              // 전체 융자 잔고 비율
    }

    // =========================
    // output2 : 기간별 시세
    // =========================
    @Getter
    @Setter
    public static class DailyPrice {

        @JsonProperty("stck_bsop_date")
        private String businessDate;                // 영업일자 (YYYYMMDD)

        @JsonProperty("stck_clpr")
        private String closePrice;                  // 종가

        @JsonProperty("stck_oprc")
        private String openPrice;                   // 시가

        @JsonProperty("stck_hgpr")
        private String highPrice;                   // 고가

        @JsonProperty("stck_lwpr")
        private String lowPrice;                    // 저가

        @JsonProperty("acml_vol")
        private String accumulatedVolume;           // 누적 거래량

        @JsonProperty("acml_tr_pbmn")
        private String accumulatedTradeAmount;      // 누적 거래대금

        @JsonProperty("flng_cls_code")
        private String fallingClassCode;            // 락 구분 코드

        @JsonProperty("prtt_rate")
        private String splitRate;                   // 분할 비율

        @JsonProperty("mod_yn")
        private String modified;                    // 변경 여부 (Y/N)

        @JsonProperty("prdy_vrss_sign")
        private String changeSign;                  // 전일 대비 부호

        @JsonProperty("prdy_vrss")
        private String changeFromPrevDay;           // 전일 대비

        @JsonProperty("revl_issu_reas")
        private String revaluationReasonCode;       // 재평가 사유 코드
    }
}
