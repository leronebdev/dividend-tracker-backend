package com.serverside.dt.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dividend_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DividendEvent {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "stock_id", nullable = false)
    private UUID stockId;

    @Column(name = "shares", nullable = false)
    private Double shares;

    @Column(name = "dividend_per_share", nullable = false)
    private Double dividendPerShare;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "payout_date", nullable = false)
    private LocalDate payoutDate;

    @Column(name = "frequency_id", nullable = false)
    private Integer frequencyId;

    @Column(name = "source", nullable = false, length = 20)
    private String source;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}