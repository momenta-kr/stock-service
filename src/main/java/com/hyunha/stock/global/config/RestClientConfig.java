package com.hyunha.stock.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${kis.base-url}")
    private String kisUrl;

    @Bean
    public RestClient kisRestClient(RestClient.Builder builder) {
        return builder.baseUrl(kisUrl).build();
    }

}
