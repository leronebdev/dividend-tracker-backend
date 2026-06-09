package com.serverside.dt.repositories.projections;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface StockAccountProjection {

    UUID getStockId();
    String getTicker();
    String getCompany();
    String getAccountNumber();
    String getCurrencyCode();

    BigDecimal getShares();
    BigDecimal getAveragePrice();
    LocalDate getSoldDate();

    BigDecimal getDividendPerShare();
    LocalDate getExDate();
    Integer getPayoutFrequency();

    String[] getPayoutDates();     // ARRAY → String[]
    LocalDate getLastPayoutDate();
}
