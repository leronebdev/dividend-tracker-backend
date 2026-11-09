package com.serverside.dt.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.serverside.dt.dtos.DividendDetailTO;
import com.serverside.dt.dtos.StockDividendDetailsDTO;

public interface DividendDetailService {

	DividendDetailTO create(DividendDetailTO detail);
	StockDividendDetailsDTO getLatestDividendDetailsByStockId(UUID stockId);
	Optional<StockDividendDetailsDTO> getDividendDetails(String stockId, LocalDate exDate, BigDecimal dividendPerShare);
	void update(DividendDetailTO detail);
	Map<String,List<DividendDetailTO>> getAllDividendDetails(String accountNumber, String stockId);
	void delete(String id,String stockId, String accountNumber);

}