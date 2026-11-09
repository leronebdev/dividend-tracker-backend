package com.serverside.dt.records;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DividendRecord(
        String ticker,
        LocalDate exDate,
        LocalDate paymentDate,
        BigDecimal dividendPerShare
) {
}
