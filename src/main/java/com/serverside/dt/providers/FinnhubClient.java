package com.serverside.dt.providers;
import java.math.BigDecimal;
public interface FinnhubClient
{
    BigDecimal getCurrentPrice(String ticker);
}