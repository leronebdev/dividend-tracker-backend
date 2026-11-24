package com.serverside.dt.clients.impl;


import java.math.BigDecimal;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import com.serverside.dt.clients.AlphaVantageClient;
import com.serverside.dt.config.AlphaVantageProperties;
import com.serverside.dt.mappers.AlphaVantageGlobalQuoteMapper;
import com.serverside.dt.records.AlphaVantageGlobalQuoteResponse;
import com.serverside.dt.records.StockPriceRecord;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class AlphaVantageClientImpl implements AlphaVantageClient {
	
	
	private final RestClient restClient;
    private final AlphaVantageProperties alphaVantageProperties;
    @Autowired
    private CacheManager cacheManager;
    private final AlphaVantageGlobalQuoteMapper alphaVantageGlobalQuoteMapper;
	
    public AlphaVantageClientImpl(@Qualifier("alphaVantageClient") RestClient restClient, AlphaVantageProperties alphaVantageProperties,
			AlphaVantageGlobalQuoteMapper alphaVantageGlobalQuoteMapper) {
		super();
		this.restClient = restClient;
		this.alphaVantageProperties = alphaVantageProperties;
		this.alphaVantageGlobalQuoteMapper = alphaVantageGlobalQuoteMapper;
	}
    
       
	@Override
	@Cacheable("stockPricesAV")
	public BigDecimal getCurrentPrice(String ticker) {	    
	   

		URI uri = UriComponentsBuilder
		        .fromPath("/query")
		        .queryParam("symbol", ticker)
		        .queryParam("function", "GLOBAL_QUOTE")
		        .queryParam("apikey", alphaVantageProperties.getApiKey())
		        .build()
		        .toUri();
		log.info("Calling AlphaVantage URI: {}", uri);
		AlphaVantageGlobalQuoteResponse response = restClient.get()
		        .uri(uri)
		        .retrieve()
		        .body(AlphaVantageGlobalQuoteResponse.class);
		
		if (response == null || response.globalQuote() == null) {
			log.error("No quote returned for " + ticker);
			return new BigDecimal(0);
		}
		StockPriceRecord stockPriceRecord = alphaVantageGlobalQuoteMapper.toRecord(response);
		if(stockPriceRecord == null)
		    System.out.println("TICKER: " + ticker + " Failed!!!!");
		return stockPriceRecord.currentPrice();

	}

	

}
