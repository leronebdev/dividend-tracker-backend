package com.serverside.dt.providers;

import java.util.List;

import com.serverside.dt.records.DividendRecord;

public interface DividendDataProvider {

    List<DividendRecord> getDividends(String ticker);

}