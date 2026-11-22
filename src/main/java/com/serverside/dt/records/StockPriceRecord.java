package com.serverside.dt.records;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StockPriceRecord(
        String ticker,
        BigDecimal currentPrice,
        BigDecimal previousClose,
        BigDecimal change,
        LocalDate lastTradingDay
) {
}