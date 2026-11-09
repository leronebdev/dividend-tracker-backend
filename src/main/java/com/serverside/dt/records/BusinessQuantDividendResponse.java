package com.serverside.dt.records;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BusinessQuantDividendResponse(
        Metadata metadata,
        List<DividendData> data
) {

    public record Metadata(
            Long cik,
            String ticker,
            String id_series,
            String companyname,
            String companyname_short,
            String range,
            BigDecimal divyield,
            String lastdividend,
            String nextdividend,
            BigDecimal ttmdividend,
            String mode,
            String note
    ) {
    }

    public record DividendData(
            BigDecimal dividend,

            @JsonProperty("ex_date")
            LocalDate exDate,

            @JsonProperty("payment_date")
            LocalDate paymentDate
    ) {
    }
}