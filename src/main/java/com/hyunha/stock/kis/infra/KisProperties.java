package com.hyunha.stock.kis.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kis")
public record KisProperties(String appKey, String appSecret) {}
