package com.serverside.dt.clients;

import java.math.BigDecimal;

public interface FinnhubClient {

    BigDecimal getCurrentPrice(String ticker);
}