package com.serverside.dt.commands;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.serverside.dt.Currency;
import com.serverside.dt.entities.Account;
import com.serverside.dt.entities.AccountType;
import com.serverside.dt.entities.TaxRule;
import com.serverside.dt.services.AccountService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CalculateTaxCommand implements ICalculateCommand {
	private final AccountService accountService;	

	@Override
	public void calculate(String accountNumber,ICalculatableDividend calculatableDividend) {
		Account account = accountService.getEntityByAccountNumber(accountNumber);
		AccountType accountType = account.getAccountType();
		TaxRule taxRule = accountType.getTaxRule();
		BigDecimal afterTaxAmount = calculatableDividend.getDividendPerShare().multiply(calculatableDividend.getShares());
		
		if(Currency.isValid(calculatableDividend.getCurrency()) && Currency.USD == Currency.from(calculatableDividend.getCurrency()))
		{
			BigDecimal taxesPaid = afterTaxAmount.multiply(taxRule.getWithholdRate());
			calculatableDividend.setTaxesPaid(taxesPaid);
			afterTaxAmount = afterTaxAmount.subtract(taxesPaid);
		}

		calculatableDividend.setAmount(afterTaxAmount);
	}

}
