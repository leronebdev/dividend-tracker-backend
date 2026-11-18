package com.serverside.dt.services;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
public interface StockPriceService
{
    Map<String, BigDecimal> getCurrentPrices(List<String> tickers);
}
