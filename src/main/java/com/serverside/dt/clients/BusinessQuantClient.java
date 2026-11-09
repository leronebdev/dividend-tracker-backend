package com.serverside.dt.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.serverside.dt.config.BusinessQuantProperties;
import com.serverside.dt.records.BusinessQuantDividendResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BusinessQuantClient {

    private final RestClient restClient;
    private final BusinessQuantProperties businessQuantProps;

    public BusinessQuantDividendResponse getDividends(String ticker) {

        log.debug("Retrieving dividend information for ticker {}", ticker);

        return restClient.get()
                .uri(uriBuilder -> uriBuilder                                                
                        .path("/dividends")
                        .queryParam("ticker", ticker)
                        .queryParam("mode", "dps")
                        .queryParam("api_key", businessQuantProps.getApiKey())
                        .build())
                .retrieve()
                .body(BusinessQuantDividendResponse.class);
    }
}