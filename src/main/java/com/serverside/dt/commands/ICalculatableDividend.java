package com.serverside.dt.commands;

import java.math.BigDecimal;

public interface ICalculatableDividend {
	
	public BigDecimal getDividendPerShare();	
	public BigDecimal getShares();
	public String getCurrency();	
	public void setAmount(BigDecimal amount);
	public void setTaxesPaid(BigDecimal taxesPaid);

}
