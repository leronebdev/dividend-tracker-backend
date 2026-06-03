package com.serverside.dt.dtos;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectorDTO {

    private Integer id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}