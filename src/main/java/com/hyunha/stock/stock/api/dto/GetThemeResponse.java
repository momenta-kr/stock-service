package com.hyunha.stock.stock.api.dto;

import com.hyunha.stock.stock.infra.redis.dto.DomesticStockCurrentPriceResponse;

import java.util.List;

public record GetThemeResponse(
        String themeCode,
        String themeName,
        Double averageChangeRateFromPreviousDay,
        List<Output> stockInfoList
        ) {

        public record Output(
                String stockName,

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

                // ✅ 1) "원본 DTO -> record" 변환 생성자
                public Output(String stockName, DomesticStockCurrentPriceResponse.Output src) {
                        this(
                                stockName,
                                src.getStockStatusClassificationCode(),
                                src.getMarginRate(),
                                src.getRepresentativeMarketKoreanName(),
                                src.getNewHighLowClassificationCode(),
                                src.getIndustryKoreanName(),
                                src.getTemporaryStopYn(),
                                src.getOpenPriceRangeExtensionYn(),
                                src.getClosePriceRangeExtensionYn(),
                                src.getCreditAvailableYn(),
                                src.getGuaranteeDepositRateClassificationCode(),
                                src.getElwIssuedYn(),
                                src.getCurrentPrice(),
                                src.getChangeFromPreviousDay(),
                                src.getChangeSignFromPreviousDay(),
                                src.getChangeRateFromPreviousDay(),
                                src.getAccumulatedTradeAmount(),
                                src.getAccumulatedVolume(),
                                src.getVolumeChangeRateFromPreviousDay(),
                                src.getOpenPrice(),
                                src.getHighPrice(),
                                src.getLowPrice(),
                                src.getUpperLimitPrice(),
                                src.getLowerLimitPrice(),
                                src.getBasePrice(),
                                src.getWeightedAveragePrice(),
                                src.getHtsForeignExhaustionRate(),
                                src.getForeignNetBuyQuantity(),
                                src.getProgramNetBuyQuantity(),
                                src.getPivotSecondResistancePrice(),
                                src.getPivotFirstResistancePrice(),
                                src.getPivotPointValue(),
                                src.getPivotFirstSupportPrice(),
                                src.getPivotSecondSupportPrice(),
                                src.getResistanceValue(),
                                src.getSupportValue(),
                                src.getCapitalAmount(),
                                src.getPriceLimitWidth(),
                                src.getFaceValue(),
                                src.getSubstitutePrice(),
                                src.getQuoteUnit(),
                                src.getHtsTradeQuantityUnitValue(),
                                src.getListedShares(),
                                src.getHtsMarketCap(),
                                src.getPer(),
                                src.getPbr(),
                                src.getSettlementMonth(),
                                src.getVolumeTurnoverRate(),
                                src.getEps(),
                                src.getBps(),
                                src.getDay250HighPrice(),
                                src.getDay250HighPriceDate(),
                                src.getDay250HighPriceVsCurrentRate(),
                                src.getDay250LowPrice(),
                                src.getDay250LowPriceDate(),
                                src.getDay250LowPriceVsCurrentRate(),
                                src.getYearlyHighPrice(),
                                src.getYearlyHighPriceVsCurrentRate(),
                                src.getYearlyHighPriceDate(),
                                src.getYearlyLowPrice(),
                                src.getYearlyLowPriceVsCurrentRate(),
                                src.getYearlyLowPriceDate(),
                                src.getWeek52HighPrice(),
                                src.getWeek52HighPriceVsCurrentRate(),
                                src.getWeek52HighPriceDate(),
                                src.getWeek52LowPrice(),
                                src.getWeek52LowPriceVsCurrentRate(),
                                src.getWeek52LowPriceDate(),
                                src.getTotalLoanBalanceRate(),
                                src.getShortSellingAvailableYn(),
                                src.getStockShortCode(),
                                src.getFaceValueCurrencyName(),
                                src.getCapitalCurrencyName(),
                                src.getApproachRate(),
                                src.getForeignHoldingQuantity(),
                                src.getViAppliedClassificationCode(),
                                src.getAfterHoursViAppliedClassificationCode(),
                                src.getLastShortSellingContractQuantity(),
                                src.getInvestmentCautionYn(),
                                src.getMarketWarningCode(),
                                src.getShortTermOverheatedYn(),
                                src.getLiquidationTradeYn(),
                                src.getManagedStockYn()
                        );
                }

                // ✅ 2) null-safe static factory
                public static Output from(String stockName, DomesticStockCurrentPriceResponse.Output src) {
                        return (src == null) ? null : new Output(stockName, src);
                }

        }

}
