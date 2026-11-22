package com.serverside.dt.clients.impl;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.serverside.dt.clients.EodHdClient;
import com.serverside.dt.config.EodHdProperties;
import com.serverside.dt.records.EodHdDividendResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EodHdClientImpl implements EodHdClient {

	private final RestClient restClient;
	private final EodHdProperties eodhdProperties;

	public EodHdClientImpl(@Qualifier("eodhdClient") RestClient restClient, EodHdProperties eodhdProperties) {
		this.restClient = restClient;
		this.eodhdProperties = eodhdProperties;
	}

	@Override
	public List<EodHdDividendResponse> getDividends(String ticker) {

		log.debug("Retrieving dividend information for ticker {}", ticker);

		try {

			URI uri = UriComponentsBuilder.fromUriString(eodhdProperties.getBaseUrl()).path("/div/{ticker}")
					.queryParam("api_token", eodhdProperties.getApiKey()).queryParam("fmt", "json")
					.buildAndExpand(ticker).toUri();

			log.info("EODHD Request URL: {}", uri);

			return restClient.get().uri(uri).retrieve()
					.body(new ParameterizedTypeReference<List<EodHdDividendResponse>>() {
					});

		} catch (Exception e) {

			log.error("Failed to retrieve dividend information for ticker {}", ticker, e);

			return Collections.emptyList();
		}
	}

}
