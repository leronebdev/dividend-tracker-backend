package com.serverside.dt.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
@Configuration
public class RestClientConfig
{
    @Bean("businessQuantClient")
    public RestClient businessQuantRestClient(BusinessQuantProperties properties) {
	return RestClient.builder().baseUrl(properties.getBaseUrl()).build();
    }
    @Bean("finnHubClient")
    public RestClient getFinnHubClient(FinnhubProperties properties) {
	return RestClient.builder().baseUrl(properties.getBaseUrl()).build();
    }
}
