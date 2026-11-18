package com.serverside.dt.jobs.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.dtos.StockDTO;
import com.serverside.dt.events.DividendDiscoveredEvent;
import com.serverside.dt.providers.DividendDataProvider;
import com.serverside.dt.records.DividendRecord;
import com.serverside.dt.services.AccountService;
import com.serverside.dt.services.AccountStockService;
import com.serverside.dt.services.DividendDetailService;
import com.serverside.dt.services.DividendEventService;
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
	private final DividendDataProvider dividendDataProvider;
	private final DividendReconciliationService dividendReconciliationService;
	DividendEventService dividendEventService;
	private final DividendDetailService dividendDetailService;
	private final AccountService accountService;
	private final ApplicationEventPublisher eventPublisher;

	public synchronized void synchronize() {

		log.info("Starting dividend synchronization.");

		accountStockService.getAll().forEach(this::synchronize);

		log.info("Dividend synchronization completed.");
	}

	private void synchronize(AccountStockDTO accountStock) {
		StockDTO stock = new StockDTO();
		try {
			 stock = stockService.getById(accountStock.getId());
			AccountDTO account = accountService.getById(accountStock.getId());

			Set<UUID> dividendDetailIds = dividendEventService
					.getByAccountAndStockId(account.getId(), stock.getId()).stream().map(DividendEventDTO::getStockDividendDetailId).distinct().collect(Collectors.toSet());

			var dividendDetails = dividendDetailService.getAllDividendDetailsByIds(dividendDetailIds);
			

			log.debug("Reconciling dividends for ticker [{}]", stock.getTicker());
			List<DividendRecord> dividendsFromAPI = dividendDataProvider.getDividends(stock.getTicker());

			List<DividendRecord> missingDividends = dividendReconciliationService.reconcile(accountStock,
					dividendDetails, dividendsFromAPI);

			missingDividends.forEach(
					dividend -> eventPublisher.publishEvent(new DividendDiscoveredEvent(accountStock.getStockId(),
							dividend.exDate(), dividend.paymentDate(), dividend.dividendPerShare())));

		} catch (Exception ex) {

			log.error("Failed to synchronize dividends for ticker [{}]", stock.getTicker(), ex);
		}
	}
}