package com.serverside.dt.mappers;

import org.springframework.stereotype.Component;

import com.serverside.dt.records.AlphaVantageGlobalQuoteResponse;
import com.serverside.dt.records.StockPriceRecord;

@Component
public class AlphaVantageGlobalQuoteMapper {

    public StockPriceRecord toRecord(
            AlphaVantageGlobalQuoteResponse response) {

        if (response == null
                || response.globalQuote() == null) {
            return null;
        }

        AlphaVantageGlobalQuoteResponse.GlobalQuote quote =
                response.globalQuote();

        return new StockPriceRecord(
                quote.symbol(),
                quote.price(),
                quote.previousClose(),
                quote.change(),
                quote.latestTradingDay());
    }
}