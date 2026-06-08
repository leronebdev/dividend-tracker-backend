package com.serverside.dt.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_stocks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_stock_id")
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "stock_id", nullable = false)
    private UUID stockId;
    @Column(name = "purchased_date")
    private LocalDate purchasedDate;

    @Column(name = "sold_date")
    private LocalDate soldDate;

    @Column(name = "shares", nullable = false, precision = 18, scale = 4)
    private BigDecimal  shares;

    @Column(name = "average_price", nullable = false, precision = 18, scale = 4)
    private BigDecimal  averagePrice;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}
