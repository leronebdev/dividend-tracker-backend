package com.serverside.dt.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DividendEventRequestDTO {
    private String id;
    private String stockId;
    private String ticker;
    private String account;
    private Integer shares;
    private BigDecimal dividendPerShare;
    private BigDecimal amount;
    private String currency;
    private String payoutDate;
    private String frequency;
    private String source;
}
