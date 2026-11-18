package com.serverside.dt.providers;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
public class FinnhubClientImpl implements FinnhubClient {

    private final RestClient restClient;

    @Value("${finnhub.api.key}")
    private String apiKey;

    public FinnhubClientImpl(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://finnhub.io/api/v1")
                .build();
    }

    @Override
    public BigDecimal getCurrentPrice(String ticker) {

        QuoteResponse response =
                restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/quote")
                                .queryParam("symbol", ticker)
                                .queryParam("token", apiKey)
                                .build())
                        .retrieve()
                        .body(QuoteResponse.class);

        if (response == null || response.c() == null) {
            throw new IllegalStateException(
                    "Unable to retrieve quote for ticker "
                            + ticker);
        }

        return response.c();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record QuoteResponse(
            BigDecimal c,
            BigDecimal h,
            BigDecimal l,
            BigDecimal o,
            BigDecimal pc) {
    }
}