package com.serverside.dt.records;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EodHdDividendResponse(
        LocalDate date,
        LocalDate declarationDate,
        LocalDate recordDate,
        LocalDate paymentDate,
        String period,
        BigDecimal value,
        BigDecimal unadjustedValue,
        String currency
) {
}