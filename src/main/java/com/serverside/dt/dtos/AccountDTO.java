package com.serverside.dt.dtos;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private UUID id;
    private String accountName;
    private String accountNumber;
    private String accountType;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}