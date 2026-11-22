package com.serverside.dt.providers;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DividendDataProviderFactory {

    private final List<DividendDataProvider> providers;

    public DividendDataProviderFactory(
            List<DividendDataProvider> providers) {

        this.providers = providers;
    }

    public DividendDataProvider getProvider(
            String ticker) {

        return providers.stream()
                .filter(provider ->
                        provider.supports(ticker))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No DividendDataProvider found for ticker "
                                        + ticker));
    }
}