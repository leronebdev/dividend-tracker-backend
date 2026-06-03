package com.serverside.dt.dtos;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    private UUID id;
    private String ticker;
    private String companyName;
    private Integer sectorId;
    private LocalDate exDate;
    private Integer currencyId;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}