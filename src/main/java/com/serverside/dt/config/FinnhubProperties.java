package com.serverside.dt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@ConfigurationProperties(prefix = "dividend-tracker.finnhub")
@Data
public class FinnhubProperties
{
    private String baseUrl;
    private String apiKey;
}
