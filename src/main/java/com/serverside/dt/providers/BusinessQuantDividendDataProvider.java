package com.serverside.dt.providers;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.serverside.dt.clients.BusinessQuantClient;
import com.serverside.dt.records.BusinessQuantDividendResponse;
import com.serverside.dt.records.DividendRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BusinessQuantDividendDataProvider implements DividendDataProvider {

	private final BusinessQuantClient businessQuantClient;

	@Override
	public List<DividendRecord> getDividends(String ticker) {

		log.debug("Retrieving dividend data from BusinessQuant for ticker [{}]", ticker);

		BusinessQuantDividendResponse response = businessQuantClient.getDividends(ticker);

		if (response == null || response.data() == null || response.data().isEmpty()) {

			log.debug("No dividend data returned for ticker [{}]", ticker);

			return Collections.emptyList();
		}

		return response.data().stream().map(dividend -> toDividendRecord(ticker, dividend)).toList();
	}

	private DividendRecord toDividendRecord(String ticker, BusinessQuantDividendResponse.DividendData dividend) {

		return new DividendRecord(ticker, dividend.exDate(), dividend.paymentDate(), dividend.dividend());
	}
}