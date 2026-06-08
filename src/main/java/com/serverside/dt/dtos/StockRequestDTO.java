package com.serverside.dt.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRequestDTO {

    private String id;                     // uuid
    private String ticker;
    private String company;
    private String account;                // accountNumber or null
    private String currency;               // "CAD" | "USD"
    private BigDecimal shares;
    private BigDecimal avgCost;
    private String purchaseDate;           // ISO string or null

    private BigDecimal dividendYield;
    private BigDecimal dividendPerShare;
    private String payoutFrequency;        // PayoutFrequencyKey
    private String exDividendDate;         // ISO string or null
    private String soldDate;               // ISO string or null

    private List<String> payoutDates;      // ISO strings
    private String lastPayoutDate;         // optional
}
