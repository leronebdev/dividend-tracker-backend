package com.serverside.dt.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Currency")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Integer id;

    @Column(length = 3, nullable = false, unique = true)
    private String code;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 5)
    private String symbol;

    @Column(name = "exchange_rate_to_cad", nullable = false)
    private Double exchangeRateToCad;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}