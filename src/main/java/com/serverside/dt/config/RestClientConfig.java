package com.serverside.dt.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
@Configuration("restClientConfig")
public class RestClientConfig
{
    @Bean("businessQuantApiClient")
    public RestClient businessQuantRestClient(BusinessQuantProperties properties) {
	return RestClient.builder().baseUrl(properties.getBaseUrl()).build();
    }
    @Bean("finnHubClient")
    public RestClient getFinnHubClient(FinnhubProperties properties) {
	return RestClient.builder().baseUrl(properties.getBaseUrl()).build();
    }
    @Bean("alphaVantageClient")
    public RestClient getAlphaVantageClient(AlphaVantageProperties properties) {
	return RestClient.builder().baseUrl(properties.getBaseUrl()).build();
    }
    
    @Bean("eodhdClient")
    public RestClient getEoodHdClient(EodHdProperties properties) {
	return RestClient.builder().baseUrl(properties.getBaseUrl()).build();
    }
    
    
}
