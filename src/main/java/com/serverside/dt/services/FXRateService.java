package com.serverside.dt.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.serverside.dt.dtos.FXRateResult;

import tools.jackson.databind.ObjectMapper;

@Service
public class FXRateService {

    @Value("${dividend-tracker.fx.api-url}")
    private String fxApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public FXRateResult getFxRateOnDate(LocalDate date, String from, String to) {

        String url = String.format("%s/%s?from=%s&to=%s",
                fxApiUrl,
                date.toString(),   // LocalDate → "YYYY-MM-DD"
                from,
                to
        );

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("rates")) {
            throw new RuntimeException("FX API returned no data");
        }

        Map<String, Double> rates = (Map<String, Double>) response.get("rates");

        if (!rates.containsKey(to)) {
            throw new RuntimeException("FX API returned no rate for " + to);
        }

        double rate = rates.get(to);
        String returnedDate = (String) response.get("date");

        return new FXRateResult(returnedDate, from, to, new BigDecimal(rate));
    }

    public FXRateResult getTodayRate(String from, String to) {        
        return getFxRateOnDate(java.time.LocalDate.now(), from, to);
    }
}
