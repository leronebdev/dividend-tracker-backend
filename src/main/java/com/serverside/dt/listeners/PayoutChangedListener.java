package com.serverside.dt.listeners;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.dtos.StockDividendDetailsDTO;
import com.serverside.dt.entities.DividendEvent;
import com.serverside.dt.events.PayoutDateAddedEvent;
import com.serverside.dt.events.PayoutDateRemovedEvent;
import com.serverside.dt.services.AccountStockService;
import com.serverside.dt.services.DividendEventService;

@Component
public class PayoutChangedListener {

    @Autowired
    private DividendEventService dividendEventsService;

    @Autowired
    private AccountStockService accountService;

    @EventListener
    @Transactional
    public void handlePayoutAdded(PayoutDateAddedEvent event) {

        StockDividendDetailsDTO details = event.getDto();

        // 1️⃣ Fetch all account-stock holdings for this stock
        List<AccountStockDTO> holdings =
                accountService.getByStyockId(details.getStockId().toString());

        for (AccountStockDTO holding : holdings) {

            UUID accountId = holding.getAccountId();
            UUID stockId = details.getStockId();
            UUID stockDividendDetailId = details.getId();
            LocalDate exDate = details.getExDate();

            // 2️⃣ Skip if stock was sold before ex-date
            if (holding.getSoldDate() != null &&
                holding.getSoldDate().isBefore(exDate)) {
                continue;
            }

            // 3️⃣ Compute shares and total amount
            BigDecimal shares = holding.getShares();
            BigDecimal totalAmount = shares.multiply(details.getDividendPerShare());

            // 4️⃣ FX rate (placeholder or dynamic)
            BigDecimal fxRate = new BigDecimal("1.36");

            // 5️⃣ Check if a dividend event already exists for this account + stock + detail
            List<DividendEventDTO> existingEvents =
                    dividendEventsService.getByAccount(accountId)
                            .stream()
                            .filter(e ->
                                    e.getStockId().equals(stockId) &&
                                    e.getStockDividendDetailId().equals(stockDividendDetailId)
                            )
                            .toList();

            if (!existingEvents.isEmpty()) {
                // 6️⃣ Update existing event (only mutable fields)
                DividendEventDTO existing = existingEvents.get(0);

                existing.setSharesAtEvent(shares);
                existing.setTotalAmount(totalAmount);
                existing.setFxRate(fxRate);

                dividendEventsService.update(existing.getId(), existing);
                continue;
            }

            // 7️⃣ Create new dividend event
            DividendEvent newEvent = DividendEvent.builder()
                    .stockId(stockId)
                    .stockDividendDetailId(stockDividendDetailId)
                    .accountId(accountId)
                    .sharesAtEvent(shares)
                    .fxRate(fxRate)
                    .totalAmount(totalAmount)
                    .createdDate(LocalDateTime.now())
                    .lastUpdatedDate(LocalDateTime.now())
                    .build();

            dividendEventsService.saveDividendEvent(newEvent);
        }
    }


    @EventListener
    public void handlePayoutRemoved(PayoutDateRemovedEvent event) {    	
    	dividendEventsService.delete(event.getStockId(), event.getPayoutDate());
    }
}
