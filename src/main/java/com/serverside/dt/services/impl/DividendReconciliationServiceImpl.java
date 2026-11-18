package com.serverside.dt.services.impl;

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

		return apiDividends.stream()
				.filter(apiDividend -> apiDividend.exDate().isAfter(accountStock.getPurchasedDate()))
				.filter(apiDividend -> dividendDetailsForAccountStock.stream()
						.noneMatch(existingDetail -> existingDetail.getExDate().equals(apiDividend.exDate())
								&& existingDetail.getDividendPerShare().compareTo(apiDividend.dividendPerShare()) == 0))
				.toList();
	}	
}