package com.serverside.dt.clients;

import java.util.List;

import com.serverside.dt.records.EodHdDividendResponse;

public interface EodHdClient {
	
	public List<EodHdDividendResponse> getDividends(String ticker);

}
