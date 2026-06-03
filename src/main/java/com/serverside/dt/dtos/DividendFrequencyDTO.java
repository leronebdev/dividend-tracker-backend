package com.serverside.dt.dtos;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DividendFrequencyDTO {

    private Integer id;
    private String name;
    private Integer periodsPerYear;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}