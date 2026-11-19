package com.serverside.dt.clients.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.serverside.dt.clients.FinnhubClient;
import com.serverside.dt.records.FinnhubQuoteResponse;

@Component
public class FinnhubClientImpl implements FinnhubClient {

	private final RestClient restClient;

	private final String apiKey;

	public FinnhubClientImpl(@Value("${dividend-tracker.finnhub.api-key}") String apiKey) {

		this.restClient = RestClient.builder().baseUrl("https://finnhub.io/api/v1").build();

		this.apiKey = apiKey;
	}

	@Override
	 @Cacheable("stock-prices")
	public BigDecimal getCurrentPrice(String ticker) {
		if(ticker.contains(".")) return new BigDecimal(0);
		FinnhubQuoteResponse response = restClient.get().uri(uriBuilder -> uriBuilder.path("/quote")
				.queryParam("symbol", ticker).queryParam("token", apiKey).build()).retrieve()
				.body(FinnhubQuoteResponse.class);

		if (response == null || response.c() == null) {
			throw new IllegalStateException("No quote returned for " + ticker);
		}

		return response.c();
	}
}