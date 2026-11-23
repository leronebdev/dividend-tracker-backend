package com.serverside.dt.providers;

import java.util.Collections;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.serverside.dt.clients.EodHdClient;
import com.serverside.dt.mappers.EodHdDividendMapper;
import com.serverside.dt.records.DividendRecord;
import com.serverside.dt.records.EodHdDividendResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EodHdDividendDataProvider implements DividendDataProvider {

	private final EodHdClient eodHdClient;
	private final EodHdDividendMapper eodHdDividendMapper;

	@Override
	@Cacheable("dividendHistoryEOD")
	public List<DividendRecord> getDividends(String ticker) {
		log.debug("Retrieving dividend data from EODHD for ticker [{}]", ticker);

		List<EodHdDividendResponse> response = eodHdClient.getDividends(ticker);		

		if (response == null) {

			log.debug("No dividend data returned from EODHD for ticker [{}]", ticker);

			return Collections.emptyList();
		}
		return eodHdDividendMapper.toDividendRecords(response, ticker);
	}

	@Override
	public boolean supports(String ticker) {

		return ticker.endsWith(".TO");
	}

}
