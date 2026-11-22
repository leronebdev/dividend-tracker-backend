package com.serverside.dt.clients.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.serverside.dt.clients.FinnhubClient;
import com.serverside.dt.config.FinnhubProperties;
import com.serverside.dt.records.FinnhubQuoteResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FinnhubClientImpl implements FinnhubClient {

    	
	private final RestClient restClient;
    private final FinnhubProperties finnhubProperties;
    	
    	
	
	public FinnhubClientImpl(@Qualifier("finnHubClient") RestClient restClient, FinnhubProperties finnhubProperties) {			
			this.restClient = restClient;
			this.finnhubProperties = finnhubProperties;
		}



	@Override
	 @Cacheable("stockPrices")
	public BigDecimal getCurrentPrice(String ticker) {
		if(ticker.contains(".")) return new BigDecimal(0);
		FinnhubQuoteResponse response = restClient.get().uri(uriBuilder -> uriBuilder.path("/quote")
				.queryParam("symbol", ticker).queryParam("token", finnhubProperties.getApiKey()).build()).retrieve()
				.body(FinnhubQuoteResponse.class);

		if (response == null || response.c() == null) {
			log.error("No quote returned for " + ticker);
			return new BigDecimal(0);
		}

		return response.c();
	}
}