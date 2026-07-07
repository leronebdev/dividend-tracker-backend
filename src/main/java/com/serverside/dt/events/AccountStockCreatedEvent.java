package com.serverside.dt.events;

import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.dtos.StockDividendDetailsDTO;

import lombok.Getter;

@Getter
public class AccountStockCreatedEvent {
	
	private final AccountStockDTO accountStock;
	private final StockDividendDetailsDTO stockDividendDetails;
	public AccountStockCreatedEvent(AccountStockDTO accountStock, StockDividendDetailsDTO stockDividendDetails) {		
		this.accountStock = accountStock;
		this.stockDividendDetails = stockDividendDetails;
	}

}
