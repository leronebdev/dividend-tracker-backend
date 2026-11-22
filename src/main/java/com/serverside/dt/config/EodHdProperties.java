package com.serverside.dt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "dividend-tracker.eodhd")
@Data
public class EodHdProperties {
        String baseUrl;
        String apiKey;
}
