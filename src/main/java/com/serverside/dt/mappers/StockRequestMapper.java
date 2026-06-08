package com.serverside.dt.mappers;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.serverside.dt.dtos.StockRequestDTO;
import com.serverside.dt.entities.Account;
import com.serverside.dt.entities.AccountStock;
import com.serverside.dt.entities.Currency;
import com.serverside.dt.entities.DividendFrequency;
import com.serverside.dt.entities.Stock;
import com.serverside.dt.entities.StockDividendDetails;

@Mapper(componentModel = "spring")
public interface StockRequestMapper {

	@Mapping(source = "stock.id", target = "id")
	@Mapping(source = "stock.ticker", target = "ticker")
	@Mapping(source = "stock.company", target = "company")
	@Mapping(source = "account.accountNumber", target = "account")
	@Mapping(source = "currency.code", target = "currency")
	@Mapping(source = "accountStock.shares", target = "shares")
	@Mapping(source = "accountStock.avgCost", target = "avgCost")
	@Mapping(source = "accountStock.purchaseDate", target = "purchaseDate", dateFormat = "yyyy-MM-dd")
	@Mapping(source = "accountStock.soldDate", target = "soldDate", dateFormat = "yyyy-MM-dd")

	// Dividend details (latest)
	@Mapping(source = "latestDetails.dividendPerShare", target = "dividendPerShare")
	@Mapping(source = "latestDetails.dividendYield", target = "dividendYield")
	@Mapping(source = "frequency.name", target = "payoutFrequency")
	@Mapping(source = "latestDetails.exDate", target = "exDividendDate", dateFormat = "yyyy-MM-dd")

	// Payout dates come from ALL StockDividendDetails rows
	@Mapping(target = "payoutDates", expression = "java(mapPayoutDates(allDetails))")
	@Mapping(target = "lastPayoutDate", expression = "java(mapLastPayoutDate(allDetails))")

	StockRequestDTO toDto(Account account, AccountStock accountStock, Stock stock, Currency currency,
			DividendFrequency frequency, StockDividendDetails latestDetails, List<StockDividendDetails> allDetails);

	// Extract all payout dates
	default List<String> mapPayoutDates(List<StockDividendDetails> details) {
		return details.stream().map(StockDividendDetails::getPayoutDate).filter(Objects::nonNull).sorted()
				.map(LocalDate::toString).toList();
	}

	// Latest payout date
	default String mapLastPayoutDate(List<StockDividendDetails> details) {
		return details.stream().map(StockDividendDetails::getPayoutDate).filter(Objects::nonNull)
				.max(LocalDate::compareTo).map(LocalDate::toString).orElse(null);
	}
}
