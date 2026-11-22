package com.serverside.dt.records;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AlphaVantageGlobalQuoteResponse(
        @JsonProperty("Global Quote")
        GlobalQuote globalQuote
) {

    public record GlobalQuote(

            @JsonProperty("01. symbol")
            String symbol,

            @JsonProperty("02. open")
            BigDecimal open,

            @JsonProperty("03. high")
            BigDecimal high,

            @JsonProperty("04. low")
            BigDecimal low,

            @JsonProperty("05. price")
            BigDecimal price,

            @JsonProperty("06. volume")
            Long volume,

            @JsonProperty("07. latest trading day")
            LocalDate latestTradingDay,

            @JsonProperty("08. previous close")
            BigDecimal previousClose,

            @JsonProperty("09. change")
            BigDecimal change,

            @JsonProperty("10. change percent")
            String changePercent
    ) {
    }
}