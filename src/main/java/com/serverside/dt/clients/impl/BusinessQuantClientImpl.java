package com.serverside.dt.clients.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.serverside.dt.clients.BusinessQuantClient;
import com.serverside.dt.config.BusinessQuantProperties;
import com.serverside.dt.records.BusinessQuantDividendResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BusinessQuantClientImpl implements BusinessQuantClient {
   
    private final RestClient restClient;
    private final BusinessQuantProperties businessQuantProps;
    
    

	public BusinessQuantClientImpl(@Qualifier("businessQuantApiClient")RestClient restClient, BusinessQuantProperties businessQuantProps) {
		super();
		this.restClient = restClient;
		this.businessQuantProps = businessQuantProps;
	}
	@Override
    public BusinessQuantDividendResponse getDividends(String ticker) {

        log.debug("Retrieving dividend information for ticker {}", ticker);

        try
        {
        BusinessQuantDividendResponse result = restClient.get()
                .uri(uriBuilder -> uriBuilder                                                
                        .path("/dividends")
                        .queryParam("ticker", ticker)
                        .queryParam("mode", "dps")
                        .queryParam("api_key", businessQuantProps.getApiKey())
                        .build())
                .retrieve()
                .body(BusinessQuantDividendResponse.class);
        return result;
        }
        catch(Exception e)
        {
        	log.error("Failed to retrieve dividend information for ticker {}: {}", ticker, e.getMessage());
        	return null;
        }
    }



}