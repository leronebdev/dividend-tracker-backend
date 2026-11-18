package com.serverside.dt.services;

import java.util.List;

import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.dtos.StockDividendDetailsDTO;
import com.serverside.dt.records.DividendRecord;

public interface DividendReconciliationService {

	List<DividendRecord> reconcile(AccountStockDTO accountStock, List<StockDividendDetailsDTO> dividendEventsForAccountStock,List<DividendRecord> apiDividends);
}