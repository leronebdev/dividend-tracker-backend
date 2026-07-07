package com.serverside.dt.events;

import com.serverside.dt.dtos.StockDividendDetailsDTO;

import lombok.Getter;

public class PayoutDateAddedEvent {
	@Getter
    private final StockDividendDetailsDTO dto;
    
    public PayoutDateAddedEvent(StockDividendDetailsDTO dto) {
    	this.dto = dto;
       
    }
   
}
