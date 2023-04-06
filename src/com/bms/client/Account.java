package com.bms.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Account {
	private String name;
	private String username;
	private String password;
	private final String accNumber;
	List<String> listOfAccNum;
	private Map<String, String> details;


	public Account(String name, String username, String password, List<String> listOfAccNum) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.listOfAccNum = listOfAccNum;
		this.accNumber = generateAccountNumber();
		this.details = accountDetails(name, username, password);
	}

	private Map<String, String> accountDetails(String name, String username, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("username", username);
		map.put("password", password);
		return map;
	}
	public Map<String, String> getDetails() {
		return details;
	}

	public String getAccNumber() {
		return accNumber;
	}


	public String generateAccountNumber() {
		UUID uuid = UUID.randomUUID();
		String accountNumber = uuid.toString().substring(0, 11);
		if(!listOfAccNum.contains(accountNumber)) {
			listOfAccNum.add(accountNumber);
		}
		else {
			accountNumber = generateAccountNumber();
		}
		return accountNumber;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
