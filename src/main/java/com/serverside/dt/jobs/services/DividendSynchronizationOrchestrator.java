package com.serverside.dt.jobs.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.dtos.DividendDetailTO;
import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.dtos.StockDTO;
import com.serverside.dt.entities.DividendFrequency;
import com.serverside.dt.providers.DividendDataProviderFactory;
import com.serverside.dt.records.DividendRecord;
import com.serverside.dt.services.AccountService;
import com.serverside.dt.services.AccountStockService;
import com.serverside.dt.services.DividendDetailService;
import com.serverside.dt.services.DividendEventService;
import com.serverside.dt.services.DividendFrequencyService;
import com.serverside.dt.services.DividendReconciliationService;
import com.serverside.dt.services.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DividendSynchronizationOrchestrator {

	private final AccountStockService accountStockService;
	private final StockService stockService;	
	private final DividendDataProviderFactory dividendProviderFactory;
	private final DividendReconciliationService dividendReconciliationService;
	private final DividendEventService dividendEventService;
	private final DividendDetailService dividendDetailService;
	private final DividendFrequencyService dividendFrequencyService;
	private final AccountService accountService;

	public synchronized void synchronize() {

		log.info("Starting dividend synchronization.");

		accountStockService.getAll().forEach(this::synchronize);

		log.info("Dividend synchronization completed.");
	}

	private void synchronize(AccountStockDTO accountStock) {
		StockDTO stock = new StockDTO();
		try {
			 stock = stockService.getById(accountStock.getStockId());
			 final UUID stockId = stock.getId();
			AccountDTO account = accountService.getById(accountStock.getAccountId());
			Optional<DividendFrequency> frequency = dividendFrequencyService.getById(stock.getPayoutFrequency());
			if (!frequency.isPresent())
			{
			    log.error("Failed, could not find frequency ID " + stock.getPayoutFrequency());
			    return;
			}
			
			Set<UUID> dividendDetailIds = dividendEventService
					.getByAccountAndStockId(account.getId(), stock.getId()).stream().map(DividendEventDTO::getStockDividendDetailId).distinct().collect(Collectors.toSet());

			var dividendDetails = dividendDetailService.getAllDividendDetailsByIds(dividendDetailIds);
			

			log.debug("Reconciling dividends for ticker [{}]", stock.getTicker());
			List<DividendRecord> dividendsFromAPI = dividendProviderFactory.getProvider(stock.getTicker()).getDividends(stock.getTicker());

			List<DividendRecord> missingDividends = dividendReconciliationService.reconcile(accountStock,
					dividendDetails, dividendsFromAPI);

			
			missingDividends.forEach(
				
					dividend -> {
					    dividendDetailService.create(
					    DividendDetailTO.builder().stockId(stockId.toString()).dividendPerShare(dividend.dividendPerShare()).exDate(dividend.exDate())
					    .frequency(frequency.get().getPeriodsPerYear()).payoutDate(dividend.paymentDate()).build());
					});
					

		} catch (Exception ex) {

			log.error("Failed to synchronize dividends for ticker [{}]", stock.getTicker(), ex);
		}
	}
}