package com.serverside.dt.dtos;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStockDTO {

    private UUID id;
    private UUID accountId;
    private UUID stockId;
    private Double quantity;
    private Double averagePrice;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}