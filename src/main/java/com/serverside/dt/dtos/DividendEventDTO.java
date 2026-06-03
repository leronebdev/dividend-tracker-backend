package com.serverside.dt.dtos;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DividendEventDTO {

    private UUID id;
    private UUID accountId;
    private UUID stockId;
    private Double shares;
    private Double dividendPerShare;
    private Double amount;
    private String currency;
    private LocalDate payoutDate;
    private Integer frequencyId;
    private String source;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}