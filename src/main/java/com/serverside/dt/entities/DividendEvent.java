package com.serverside.dt.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "dividend_event_id")
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "stock_id", nullable = false)
    private UUID stockId;

    @Column(name = "stock_dividend_detail_id", nullable = false)
    private UUID stockDividendDetailId;

    @Column(name = "tax_rule_id")
    private Integer taxRuleId;

    @Column(name = "shares_at_event", nullable = false, precision = 18, scale = 4)
    private BigDecimal sharesAtEvent;

    @Column(name = "total_amount", nullable = false, precision = 18, scale = 4)
    private BigDecimal totalAmount;

    @Column(name = "fx_rate", precision = 18, scale = 6)
    private BigDecimal fxRate;

    @Column(name = "fx_rate_used", precision = 18, scale = 6)
    private BigDecimal fxRateUsed;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;
}
