package com.serverside.dt.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tax_rule")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_rule_id")
    private Integer id;

    @Column(name = "rule_name", nullable = false, unique = true, length = 100)
    private String ruleName;

    @Column(name = "total_us_rate", precision = 5, scale = 4)
    private java.math.BigDecimal totalUSRate;
    @Column(name = "total_cad_rate", precision = 5, scale = 4)
    private java.math.BigDecimal totalCADRate;

    @Column(name = "withhold_rate", precision = 5, scale = 4)
    private java.math.BigDecimal withholdRate;

    @Column(name = "effective_date")
    private java.time.LocalDate effectiveDate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}
