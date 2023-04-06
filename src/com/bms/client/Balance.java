package com.bms.client;

public class Balance {
	private long amount;

	public Balance(long amount) {
		super();
		this.amount = amount;
	}

	public Balance() {
		super();
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
}
