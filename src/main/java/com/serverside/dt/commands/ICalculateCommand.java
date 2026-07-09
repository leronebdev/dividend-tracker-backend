package com.serverside.dt.commands;

public interface ICalculateCommand {
	
	void calculate(String accountNumber,ICalculatableDividend calculatableDividend);

}
