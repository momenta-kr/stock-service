package com.hyunha.stock.stock.api.dto;

import com.hyunha.stock.kis.infra.dto.InvestmentOpinionApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class GetInvestmentOpinionResponse {
    private String stockBusinessDate; // 주식영업일자 (YYYYMMDD)
    private String investmentOpinion; // 투자의견
    private String investmentOpinionClassCode; // 투자의견구분코드
    private String previousInvestmentOpinion; // 직전투자의견
    private String previousInvestmentOpinionClassCode; // 직전투자의견구분코드
    private String memberCompanyName; // 회원사명
    private String htsTargetPrice; // HTS목표가격
    private String previousDayClosePrice; // 주식전일종가
    private String nDayDisparity; // 주식N일괴리도
    private String nDayDisparityRate; // N일괴리율
    private String stockFuturesDisparity; // 주식선물괴리도
    private String stockFuturesDisparityRate; // 괴리율

    public static List<GetInvestmentOpinionResponse> from(List<InvestmentOpinionApiResponse.InvestmentOpinionItem> items) {
        return items.stream()
                .map(item -> {
                    GetInvestmentOpinionResponse response = new GetInvestmentOpinionResponse();
                    response.stockBusinessDate = item.getStockBusinessDate();
                    response.investmentOpinion = item.getInvestmentOpinion();
                    response.investmentOpinionClassCode = item.getInvestmentOpinionClassCode();
                    response.previousInvestmentOpinion = item.getPreviousInvestmentOpinion();
                    response.previousInvestmentOpinionClassCode = item.getPreviousInvestmentOpinionClassCode();
                    response.memberCompanyName = item.getMemberCompanyName();
                    response.htsTargetPrice = item.getHtsTargetPrice();
                    response.previousDayClosePrice = item.getPreviousDayClosePrice();
                    response.nDayDisparity = item.getNDayDisparity();
                    response.nDayDisparityRate = item.getNDayDisparityRate();
                    response.stockFuturesDisparity = item.getStockFuturesDisparity();
                    response.stockFuturesDisparityRate = item.getStockFuturesDisparityRate();
                    return response;
                })
                .toList();
    }
}

