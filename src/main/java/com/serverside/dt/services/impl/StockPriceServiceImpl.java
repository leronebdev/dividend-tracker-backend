package com.serverside.dt.services.impl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.serverside.dt.services.StockPriceService;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class StockPriceServiceImpl implements StockPriceService
{
    private final FinnhubClient finnhubClient;
    @Override
    public Map<String, BigDecimal> getCurrentPrices(List<String> tickers) {
	Map<String, BigDecimal> prices = new HashMap<>();
	for (String ticker : tickers)
	{
	    BigDecimal price = finnhubClient.getCurrentPrice(ticker);
	    prices.put(ticker, price);
	}
	return prices;
    }
}
