package com.serverside.dt.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockDividendDetailsDTO {
	private UUID id;
	private UUID stockId;
	private LocalDate payoutDate;
	private LocalDate exDate;
	private BigDecimal dividendPerShare;
	private LocalDateTime createdDate;
	private LocalDateTime lastUpdatedDate;
}
