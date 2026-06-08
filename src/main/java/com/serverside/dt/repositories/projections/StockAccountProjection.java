package com.serverside.dt.repositories.projections;

import java.math.BigDecimal;
import java.sql.Array;
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
    LocalDate getPurchaseDate();
    LocalDate getSoldDate();

    BigDecimal getDividendPerShare();
    BigDecimal getDividendYield();
    String getPayoutFrequency();
    LocalDate getExDate();

    Array getPayoutDates();     // PostgreSQL DATE[] → java.sql.Array
    LocalDate getLastPayoutDate();
}
