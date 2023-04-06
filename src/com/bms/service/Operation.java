package com.bms.service;

import java.util.Map;

public class Operation {
	private Map<String, Long> balanceSheet;

	public Operation(Map<String, Long> balanceSheet) {
		this.balanceSheet = balanceSheet;
	}

	public long checkBalance(String accNumber) {
		return balanceSheet.get(accNumber);
	}

	public void deposit(String accNumber, String amount) {
		balanceSheet.put(accNumber, balanceSheet.get(accNumber) + Long.parseLong(amount));

	}

	public void debit(String accNumber, String amount) {
		balanceSheet.put(accNumber, balanceSheet.get(accNumber) - Long.parseLong(amount));
	}

	public void transaction(String accNumber, String amount, String depAccNumber) {
		balanceSheet.put(accNumber, balanceSheet.get(accNumber) - Long.parseLong(amount));
		balanceSheet.put(depAccNumber, balanceSheet.get(depAccNumber) + Long.parseLong(amount));
	}

	public Map<String, Long> getBalanceSheet() {
		return balanceSheet;
	}

	public void setBalanceSheet(Map<String, Long> balanceSheet) {
		this.balanceSheet = balanceSheet;
	}
}
