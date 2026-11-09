package com.serverside.dt.jobs.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serverside.dt.dtos.StockDTO;
import com.serverside.dt.dtos.StockDividendDetailsDTO;
import com.serverside.dt.events.PayoutDateAddedEvent;
import com.serverside.dt.providers.DividendDataProvider;
import com.serverside.dt.records.DividendRecord;
import com.serverside.dt.services.DividendDetailService;
import com.serverside.dt.services.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DividendSynchronizationService {

	private final StockService stockService;
	private final DividendDetailService dividendDetailsService;
	private final DividendDataProvider dividendDataProvider;
	private final ApplicationEventPublisher applicationEventPublisher;

	public void synchronize() {

		log.info("Starting dividend synchronization.");

		List<StockDTO> stocks = stockService.getAll();

		for (StockDTO stock : stocks) {
			synchronizeStock(stock);
		}

		log.info("Dividend synchronization completed.");
	}

	private void synchronizeStock(StockDTO stock) {

		try {

			log.debug("Checking dividends for ticker [{}]", stock.getTicker());

			List<DividendRecord> dividends = dividendDataProvider.getDividends(stock.getTicker());

			if (dividends.isEmpty()) {

				log.debug("No dividend data returned for ticker [{}]", stock.getTicker());

				return;
			}

			DividendRecord latestApiDividend = dividends.stream().max(Comparator.comparing(DividendRecord::paymentDate))
					.orElse(null);

			if (latestApiDividend == null) {
				return;
			}

			Optional<StockDividendDetailsDTO> latestStoredDividend =
			        dividendDetailsService.getDividendDetails(
			                stock.getId().toString(),
			                latestApiDividend.exDate(),
			                latestApiDividend.dividendPerShare());

			if (latestStoredDividend.isEmpty()) {

			    log.info(
			            "No existing dividend records found for ticker [{}]. Publishing initial event.",
			            stock.getTicker());

			    publishPayoutDateAddedEvent(stock, latestApiDividend);

			    return;
			}
			
			if (isNewDividend(latestStoredDividend.get(), latestApiDividend)) {

				log.info("New dividend detected for ticker [{}].", stock.getTicker());

				publishPayoutDateAddedEvent(stock, latestApiDividend);
			}

		} catch (Exception ex) {

			log.error("Error synchronizing dividends for ticker [{}]", stock.getTicker(), ex);
		}
	}

	private boolean isNewDividend(StockDividendDetailsDTO storedDividend, DividendRecord apiDividend) {

		return apiDividend.paymentDate().isAfter(storedDividend.getPayoutDate());
	}

	private void publishPayoutDateAddedEvent(StockDTO stock, DividendRecord dividend) {

		StockDividendDetailsDTO dto = new StockDividendDetailsDTO();

		dto.setStockId(stock.getId());

		dto.setPayoutDate(dividend.paymentDate());

		dto.setExDate(dividend.exDate());

		dto.setDividendPerShare(dividend.dividendPerShare());

		dto.setCreatedDate(LocalDateTime.now());

		dto.setLastUpdatedDate(LocalDateTime.now());

		applicationEventPublisher.publishEvent(new PayoutDateAddedEvent(dto));
	}
}