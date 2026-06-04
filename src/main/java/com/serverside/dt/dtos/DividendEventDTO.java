package com.serverside.dt.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DividendEventDTO {
    private UUID id;
    private UUID accountId;
    private UUID stockId;
    private UUID stockDividendDetailId;
    private BigDecimal sharesAtEvent;
    private BigDecimal totalAmount;
    private BigDecimal fxRate;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}
