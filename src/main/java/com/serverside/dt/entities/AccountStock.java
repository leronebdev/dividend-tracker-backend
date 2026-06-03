package com.serverside.dt.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Account_Stocks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStock {

    @Id
    @Column(name = "account_stock_id")
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "stock_id", nullable = false)
    private UUID stockId;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "average_price")
    private Double averagePrice;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}