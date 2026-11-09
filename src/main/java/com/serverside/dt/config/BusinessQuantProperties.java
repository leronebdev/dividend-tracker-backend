package com.serverside.dt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "dividend-tracker.businessquant")
@Data
public class BusinessQuantProperties{
        String baseUrl;
        String apiKey;
}