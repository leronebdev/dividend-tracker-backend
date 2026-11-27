package com.serverside.dt.jobs.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.serverside.dt.Currency;
import com.serverside.dt.dtos.StockDTO;
import com.serverside.dt.enums.Cache;
import com.serverside.dt.providers.DividendDataProviderFactory;
import com.serverside.dt.services.StockService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CanadianDividendCacheRefresher implements CacheRefresher
{
    private final DividendDataProviderFactory dividendProviderFactory;
    private final StockService stockService;
    @Override
    public boolean refreshCache() {
	List<StockDTO>canadianStocks = stockService.getAll().stream().filter(s->Currency.CAD.toString().equals(s.getCurrencyCode())).distinct().collect(Collectors.toList());
	canadianStocks.forEach(canadianStock->{
	    dividendProviderFactory.getProvider(canadianStock.getTicker()).getDividends(canadianStock.getTicker());
	});
	
	return true;
    }
    @Override
    public Cache getCache() {
	
	return Cache.DIVIDEND_HISTORY_EOD;
    }
}
