package com.serverside.dt.events;

import java.time.LocalDate;

public class PayoutDateRemovedEvent {
    private final String stockId;
    private final String accountNumber;
    private final LocalDate payoutDate;

    public PayoutDateRemovedEvent(String stockId, String accountNumber, LocalDate payoutDate) {
        this.stockId = stockId;
        this.accountNumber = accountNumber;
        this.payoutDate = payoutDate;
    }

    public String getStockId() { return stockId; }
    public String getAccountNumber() { return accountNumber; }
    public LocalDate getPayoutDate() { return payoutDate; }
}
