package com.hyunha.stock.kis.application;

public final class TimeframeMapper {

  private TimeframeMapper() {}

  public static int toNmin(String tf) {
    return switch (tf) {
      case "1m" -> 1;
      case "3m" -> 3;
      case "5m" -> 5;
      case "10m" -> 10;
      case "30m" -> 30;
      case "60m" -> 60;
      default -> throw new IllegalArgumentException("unsupported tf: " + tf);
    };
  }
}
