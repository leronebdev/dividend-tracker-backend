package com.serverside.dt.listeners;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.dtos.FXRateResult;
import com.serverside.dt.dtos.StockDividendDetailsDTO;
import com.serverside.dt.entities.DividendEvent;
import com.serverside.dt.events.AccountStockCreatedEvent;
import com.serverside.dt.services.AccountStockService;
import com.serverside.dt.services.DividendEventService;
import com.serverside.dt.services.FXRateService;

@Component
public class AccountStockCreatedListener {
	@Autowired
	private DividendEventService dividendEventsService;

	@Autowired
	private AccountStockService accountService;
	@Autowired
	private FXRateService fxRateService;

	@EventListener
	public void handleAccountStockCreatedEvent(AccountStockCreatedEvent event) {

		// 5. Fetch ALL accountStocks for this stock
		StockDividendDetailsDTO details = event.getStockDividendDetails();
		AccountStockDTO holding = accountService.getByStockIdAndAccountId(event.getAccountStock().getStockId(),event.getAccountStock().getAccountId());

		BigDecimal shares = holding.getShares();
		BigDecimal totalAmount = shares.multiply(details.getDividendPerShare());
		LocalDate payoutDate = details.getPayoutDate();
		LocalDate today = LocalDate.now();

		LocalDate effectiveDate = payoutDate != null && (payoutDate.isBefore(today) || payoutDate.isEqual(today))
		        ? payoutDate
		        : today;

		FXRateResult todaysRate = fxRateService.getFxRateOnDate(effectiveDate, "USD", "CAD");

		DividendEvent dividendEvent = DividendEvent.builder().stockId(details.getStockId())
				.stockDividendDetailId(details.getId()).accountId(holding.getAccountId()).sharesAtEvent(shares)
				.fxRate(todaysRate.getRate()) // or dynamic FX
				.totalAmount(totalAmount).createdDate(LocalDateTime.now()).lastUpdatedDate(LocalDateTime.now()).build();

		dividendEventsService.saveDividendEvent(dividendEvent);

	}

}
