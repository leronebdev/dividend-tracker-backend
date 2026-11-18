package com.serverside.dt.events;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DividendDiscoveredEvent(
        UUID stockId,
        LocalDate exDate,
        LocalDate payoutDate,
        BigDecimal dividendPerShare) {
}