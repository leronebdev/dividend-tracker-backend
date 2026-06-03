package com.serverside.dt.dtos;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {

    private Integer id;
    private String code;
    private String name;
    private String symbol;
    private Double exchangeRateToCad;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}