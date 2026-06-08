package com.serverside.dt.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @Column(name = "stock_id", nullable = false)
    private UUID id;

    @Column(name = "ticker", nullable = false, unique = true, length = 20)
    private String ticker;

    @Column(name = "company", nullable = false, length = 200)
    private String companyName;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "payout_frequency", nullable = false)
    private Integer payoutFrequency;   // FK → dividend_frequency.frequency_id

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}
