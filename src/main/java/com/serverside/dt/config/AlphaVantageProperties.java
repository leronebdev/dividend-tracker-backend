package com.serverside.dt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "dividend-tracker.alphavantage")
@Data
public class AlphaVantageProperties {
        String baseUrl;
        String apiKey;
}