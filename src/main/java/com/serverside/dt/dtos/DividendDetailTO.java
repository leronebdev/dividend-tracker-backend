package com.serverside.dt.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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