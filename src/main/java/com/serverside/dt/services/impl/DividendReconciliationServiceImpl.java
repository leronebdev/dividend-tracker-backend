package com.serverside.dt.services.impl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.dtos.StockDividendDetailsDTO;
import com.serverside.dt.records.DividendRecord;
import com.serverside.dt.services.DividendReconciliationService;

@Service
public class DividendReconciliationServiceImpl implements DividendReconciliationService {

	@Override
	public List<DividendRecord> reconcile(AccountStockDTO accountStock,
			List<StockDividendDetailsDTO> dividendDetailsForAccountStock, List<DividendRecord> apiDividends) {

	    LocalDate purchasedDate = accountStock.getPurchasedDate();

	    if (purchasedDate == null) {
	        return List.of();
	    }

	    return apiDividends.stream()
	            .filter(dividend ->
	                    dividend.exDate().isAfter(purchasedDate))
	            .filter(dividend ->
	                    dividendDetailsForAccountStock.stream()
	                            .noneMatch(existingDetail ->
	                                    existingDetail.getExDate().equals(dividend.exDate())
	                                    && existingDetail.getDividendPerShare()
	                                            .compareTo(dividend.dividendPerShare()) == 0))
	            .toList();
	}	
}