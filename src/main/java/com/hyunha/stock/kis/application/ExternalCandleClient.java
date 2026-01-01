package com.hyunha.stock.kis.application;

import com.hyunha.stock.kis.infra.dto.CandlesResponse;

public interface ExternalCandleClient {
  CandlesResponse fetchCandles(String excd, String symbol, int nmin, int limit);
}
