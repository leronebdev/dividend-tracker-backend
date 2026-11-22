package com.serverside.dt.clients;

import com.serverside.dt.records.BusinessQuantDividendResponse;

public interface BusinessQuantClient {
	 public BusinessQuantDividendResponse getDividends(String ticker);
}
