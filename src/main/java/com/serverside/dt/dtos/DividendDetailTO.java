package com.serverside.dt.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DividendDetailTO
{
		String dividendDetailId;
        String stockId;
        String accountNumber;
        LocalDate exDate;
        LocalDate payoutDate;
        BigDecimal dividendPerShare;
        BigDecimal eligibleShares;
        Integer frequency;
}