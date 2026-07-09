package com.serverside.dt.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.serverside.dt.commands.ICalculatableDividend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockAccountProjectionDTO implements ICalculatableDividend{

    private String id;
    private String ticker;
    private String company;
    private String account;
    private String currency;
    private BigDecimal amount;

    private BigDecimal shares;
    private BigDecimal avgCost;
    private BigDecimal taxesPaid;

    private String purchaseDate;
    private String soldDate;

    private BigDecimal dividendPerShare;
    private BigDecimal dividendYield;
    private String payoutFrequency;
    private String exDividendDate;

    private List<String> payoutDates;
    private String lastPayoutDate;
}
