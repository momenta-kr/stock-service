package com.hyunha.stock.stock.domain.enums;

import lombok.Getter;

@Getter
public enum RankingPeriod {

    TODAY("0", "당일"),
    TWO_DAYS("1", "2일"),
    THREE_DAYS("2", "3일"),
    FIVE_DAYS("3", "5일"),
    TEN_DAYS("4", "10일"),
    TWENTY_DAYS("5", "20일"),
    THIRTY_DAYS("6", "30일"),
    SIXTY_DAYS("7", "60일"),
    ONE_HUNDRED_TWENTY_DAYS("8", "120일"),
    ONE_YEAR("9", "1년");

    /** KIS API에 전달하는 코드값 */
    private final String code;

    /** 문서/디버깅용 한글 설명 */
    private final String description;

    RankingPeriod(String code, String description) {
        this.code = code;
        this.description = description;
    }
}