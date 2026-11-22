package com.serverside.dt.clients;

import java.math.BigDecimal;

public interface AlphaVantageClient {
	BigDecimal getCurrentPrice(String ticker);
}
