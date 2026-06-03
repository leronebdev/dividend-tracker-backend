package com.serverside.dt.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "FX_History")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FxHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fx_id")
    private Integer id;

    @Column(name = "currency_id", nullable = false)
    private Integer currencyId;

    @Column(name = "fx_date", nullable = false)
    private LocalDate fxDate;

    @Column(name = "exchange_rate_to_cad", nullable = false)
    private Double exchangeRateToCad;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}