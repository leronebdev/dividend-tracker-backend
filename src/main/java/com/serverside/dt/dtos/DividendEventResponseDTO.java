package com.serverside.dt.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.serverside.dt.commands.ICalculatableDividend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DividendEventResponseDTO implements ICalculatableDividend {
    private String id;
    private String stockId;
    private String ticker;
    private String account;
    private BigDecimal shares;
    private BigDecimal dividendPerShare;
    private BigDecimal taxesPaid;
    private BigDecimal fxRate;
    private Integer taxRuleId;
    private BigDecimal amount;
    private String currency;
    private LocalDate payoutDate;
    private String frequency;
    private String source;
}