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
@Table(name = "stock_dividend_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDividendDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "stock_dividend_detail_id")
    private UUID id;

    @Column(name = "stock_id", nullable = false)
    private UUID stockId;

    @Column(name = "payout_date", nullable = false)
    private LocalDate payoutDate;

    @Column(name = "ex_date", nullable = false)
    private LocalDate exDate;

    @Column(name = "dividend_per_share", nullable = false, precision = 10, scale = 4)
    private BigDecimal dividendPerShare;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}
