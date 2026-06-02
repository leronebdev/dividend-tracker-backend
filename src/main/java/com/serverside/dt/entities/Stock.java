package com.serverside.dt.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "stocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String ticker;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal averageCost;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal shares;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal dividendYield;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Column(nullable = false)
    private String account; // e.g., TFSA, RRSP, Cash
}
