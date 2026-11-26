package com.serverside.dt.jobs.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.serverside.dt.Currency;
import com.serverside.dt.clients.AlphaVantageClient;
import com.serverside.dt.dtos.StockDTO;
import com.serverside.dt.enums.Cache;
import com.serverside.dt.services.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CanadianStockPricesCacheRefresher implements CacheRefresher
{
    private final AlphaVantageClient alphaVantageClient;
    private final StockService stockService;
    @Override
    public boolean refreshCache() {
	List<StockDTO>canadianStocks = stockService.getAll().stream().filter(s->Currency.CAD.equals(s.getCurrencyCode())).distinct().collect(Collectors.toList());	
	canadianStocks.forEach(canadianStock->{
	    BigDecimal currentPrice = alphaVantageClient.getCurrentPrice(canadianStock.getTicker());
	    if(BigDecimal.ZERO.equals(currentPrice))
	    {
		log.error("Failed to update current price in cache for " + canadianStock.getTicker());
	    }
	    else
	    {
		log.info("Successfully updated cache for " + canadianStock.getTicker());
	    }
	});
	return true;
    }
    @Override
    public Cache getCache() {
	
	return Cache.STOCK_PRICES_AV;
    }
}
