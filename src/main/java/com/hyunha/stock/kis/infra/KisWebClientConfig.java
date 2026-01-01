package com.hyunha.stock.kis.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class KisWebClientConfig {

  @Bean
  public WebClient kisWebClient(WebClient.Builder builder) {
    return builder
        .baseUrl("https://openapi.koreainvestment.com:9443")
        .build();
  }
}
