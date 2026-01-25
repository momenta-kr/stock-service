package com.hyunha.stock.stock.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockMasterId implements Serializable {

    @Column(name = "market", nullable = false)
    private String market;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockMasterId that)) return false;
        return Objects.equals(market, that.market) && Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(market, symbol);
    }

    public static StockMasterId kospi(String symbol) {
        StockMasterId stockMasterId = new StockMasterId();
        stockMasterId.market = "KOSPI";
        stockMasterId.symbol = symbol;
        return stockMasterId;
    }

    public static StockMasterId kosdaq(String symbol) {
        StockMasterId stockMasterId = new StockMasterId();
        stockMasterId.market = "KOSDAQ";
        stockMasterId.symbol = symbol;
        return stockMasterId;
    }
}
