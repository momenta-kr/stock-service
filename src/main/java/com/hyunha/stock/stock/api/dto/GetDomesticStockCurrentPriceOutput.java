package com.hyunha.stock.stock.api.dto;

import com.hyunha.stock.stock.infra.redis.dto.DomesticStockCurrentPriceResponse;

/**
 * DomesticStockCurrentPriceResponse.Output -> record 전용 모델
 * - Jackson 어노테이션 없음
 * - 기존 Output DTO에서 생성자/팩토리로 매핑해서 사용
 */
public record GetDomesticStockCurrentPriceOutput(

        String stockStatusClassificationCode,
        String marginRate,
        String representativeMarketKoreanName,
        String newHighLowClassificationCode,
        String industryKoreanName,
        String temporaryStopYn,
        String openPriceRangeExtensionYn,
        String closePriceRangeExtensionYn,
        String creditAvailableYn,
        String guaranteeDepositRateClassificationCode,
        String elwIssuedYn,

        String currentPrice,
        String changeFromPreviousDay,
        String changeSignFromPreviousDay,
        String changeRateFromPreviousDay,

        String accumulatedTradeAmount,
        String accumulatedVolume,
        String volumeChangeRateFromPreviousDay,

        String openPrice,
        String highPrice,
        String lowPrice,
        String upperLimitPrice,
        String lowerLimitPrice,
        String basePrice,
        String weightedAveragePrice,

        String htsForeignExhaustionRate,
        String foreignNetBuyQuantity,
        String programNetBuyQuantity,

        String pivotSecondResistancePrice,
        String pivotFirstResistancePrice,
        String pivotPointValue,
        String pivotFirstSupportPrice,
        String pivotSecondSupportPrice,

        String resistanceValue,
        String supportValue,

        String capitalAmount,
        String priceLimitWidth,
        String faceValue,
        String substitutePrice,
        String quoteUnit,
        String htsTradeQuantityUnitValue,

        String listedShares,
        String htsMarketCap,
        String per,
        String pbr,
        String settlementMonth,
        String volumeTurnoverRate,
        String eps,
        String bps,

        String day250HighPrice,
        String day250HighPriceDate,
        String day250HighPriceVsCurrentRate,
        String day250LowPrice,
        String day250LowPriceDate,
        String day250LowPriceVsCurrentRate,

        String yearlyHighPrice,
        String yearlyHighPriceVsCurrentRate,
        String yearlyHighPriceDate,
        String yearlyLowPrice,
        String yearlyLowPriceVsCurrentRate,
        String yearlyLowPriceDate,

        String week52HighPrice,
        String week52HighPriceVsCurrentRate,
        String week52HighPriceDate,
        String week52LowPrice,
        String week52LowPriceVsCurrentRate,
        String week52LowPriceDate,

        String totalLoanBalanceRate,
        String shortSellingAvailableYn,
        String stockShortCode,

        String faceValueCurrencyName,
        String capitalCurrencyName,
        String approachRate,

        String foreignHoldingQuantity,
        String viAppliedClassificationCode,
        String afterHoursViAppliedClassificationCode,
        String lastShortSellingContractQuantity,

        String investmentCautionYn,
        String marketWarningCode,
        String shortTermOverheatedYn,
        String liquidationTradeYn,
        String managedStockYn
) {
    /**
     * ✅ Output DTO를 받아 record로 매핑하는 "추가 생성자"
     */
    public GetDomesticStockCurrentPriceOutput(DomesticStockCurrentPriceResponse.Output o) {
        this(
                o.getStockStatusClassificationCode(),
                o.getMarginRate(),
                o.getRepresentativeMarketKoreanName(),
                o.getNewHighLowClassificationCode(),
                o.getIndustryKoreanName(),
                o.getTemporaryStopYn(),
                o.getOpenPriceRangeExtensionYn(),
                o.getClosePriceRangeExtensionYn(),
                o.getCreditAvailableYn(),
                o.getGuaranteeDepositRateClassificationCode(),
                o.getElwIssuedYn(),

                o.getCurrentPrice(),
                o.getChangeFromPreviousDay(),
                o.getChangeSignFromPreviousDay(),
                o.getChangeRateFromPreviousDay(),

                o.getAccumulatedTradeAmount(),
                o.getAccumulatedVolume(),
                o.getVolumeChangeRateFromPreviousDay(),

                o.getOpenPrice(),
                o.getHighPrice(),
                o.getLowPrice(),
                o.getUpperLimitPrice(),
                o.getLowerLimitPrice(),
                o.getBasePrice(),
                o.getWeightedAveragePrice(),

                o.getHtsForeignExhaustionRate(),
                o.getForeignNetBuyQuantity(),
                o.getProgramNetBuyQuantity(),

                o.getPivotSecondResistancePrice(),
                o.getPivotFirstResistancePrice(),
                o.getPivotPointValue(),
                o.getPivotFirstSupportPrice(),
                o.getPivotSecondSupportPrice(),

                o.getResistanceValue(),
                o.getSupportValue(),

                o.getCapitalAmount(),
                o.getPriceLimitWidth(),
                o.getFaceValue(),
                o.getSubstitutePrice(),
                o.getQuoteUnit(),
                o.getHtsTradeQuantityUnitValue(),

                o.getListedShares(),
                o.getHtsMarketCap(),
                o.getPer(),
                o.getPbr(),
                o.getSettlementMonth(),
                o.getVolumeTurnoverRate(),
                o.getEps(),
                o.getBps(),

                o.getDay250HighPrice(),
                o.getDay250HighPriceDate(),
                o.getDay250HighPriceVsCurrentRate(),
                o.getDay250LowPrice(),
                o.getDay250LowPriceDate(),
                o.getDay250LowPriceVsCurrentRate(),

                o.getYearlyHighPrice(),
                o.getYearlyHighPriceVsCurrentRate(),
                o.getYearlyHighPriceDate(),
                o.getYearlyLowPrice(),
                o.getYearlyLowPriceVsCurrentRate(),
                o.getYearlyLowPriceDate(),

                o.getWeek52HighPrice(),
                o.getWeek52HighPriceVsCurrentRate(),
                o.getWeek52HighPriceDate(),
                o.getWeek52LowPrice(),
                o.getWeek52LowPriceVsCurrentRate(),
                o.getWeek52LowPriceDate(),

                o.getTotalLoanBalanceRate(),
                o.getShortSellingAvailableYn(),
                o.getStockShortCode(),

                o.getFaceValueCurrencyName(),
                o.getCapitalCurrencyName(),
                o.getApproachRate(),

                o.getForeignHoldingQuantity(),
                o.getViAppliedClassificationCode(),
                o.getAfterHoursViAppliedClassificationCode(),
                o.getLastShortSellingContractQuantity(),

                o.getInvestmentCautionYn(),
                o.getMarketWarningCode(),
                o.getShortTermOverheatedYn(),
                o.getLiquidationTradeYn(),
                o.getManagedStockYn()
        );
    }

    /**
     * ✅ null-safe 팩토리 (추천)
     */
    public static GetDomesticStockCurrentPriceOutput from(DomesticStockCurrentPriceResponse.Output o) {
        return o == null ? null : new GetDomesticStockCurrentPriceOutput(o);
    }
}
