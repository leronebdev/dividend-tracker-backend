package com.serverside.dt.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id")
    private UUID id;
    
    @Column(name = "account_number", nullable = false, length = 100)
    private String accountNumber;

    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    @Column(name = "account_type", length = 50)
    private String accountType;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}