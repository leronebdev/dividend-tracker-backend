package com.serverside.dt.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class AccountStockDTO {
    private UUID id;
    private UUID accountId;
    private UUID stockId;
    private BigDecimal  shares;
    private BigDecimal  averagePrice;
    private LocalDate soldDate;
    private LocalDate purchasedDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}
