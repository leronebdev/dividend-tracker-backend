package com.serverside.dt.services;

import java.util.List;
import java.util.Map;

import com.serverside.dt.dtos.DividendDetailTO;

public interface DividendDetailService {

	DividendDetailTO create(DividendDetailTO detail);
	void update(DividendDetailTO detail);
	Map<String,List<DividendDetailTO>> getAllDividendDetails(String accountNumber, String stockId);
	void delete(String id,String stockId, String accountNumber);

}