package com.bms.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.bms.client.Account;
import com.bms.client.Balance;
import com.bms.service.Operation;
import com.bms.validate.Validate;

public class ThreadHandler implements Runnable {
	Map<String, String> validationMap;
	Map<String, Long> balanceSheet;
	List<String> listOfAccNum;
	Set<String> activeUser;
	Map<String, String> userPwd;
	Map<String, Map<String, String>> accountMap;
	Socket socket;

	Validate validate = new Validate();

	public ThreadHandler(Map<String, String> validationMap, Map<String, Long> balanceSheet, List<String> listOfAccNum,
			Set<String> activeUser, Map<String, String> userPwd, Map<String, Map<String, String>> accountMap,
			Socket socket) throws IOException {
		this.validationMap = validationMap;
		this.balanceSheet = balanceSheet;
		this.listOfAccNum = listOfAccNum;
		this.activeUser = activeUser;
		this.userPwd = userPwd;
		this.socket = socket;
		this.accountMap = accountMap;
	}

	@Override
	public void run() {
		try {
			System.out.println("Connection established");
			InputStream inputStream = socket.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream, true);
			while (true) {
				String choice = bufferedReader.readLine();

				String name = bufferedReader.readLine();
				String username = bufferedReader.readLine();
				String password = bufferedReader.readLine();
				Account account = null;
				Balance balance = null;
				String accountStatus = null;
				switch (choice) {
				case "1":
					if(!validationMap.containsKey(username)) {
						account = new Account(name, username, password, listOfAccNum);
						accountMap.put(account.generateAccountNumber(), account.getDetails()); // {accNum: {name: _,
																								// username:
																								// _, pwd: _}}
						validationMap.put(username, account.getAccNumber()); // {username: accNumber}
						balance = new Balance();
						balance.setAmount(0); // Initial balance
						balanceSheet.put(account.getAccNumber(), balance.getAmount()); // {accNumber: money}
						userPwd.put(username, password);
						accountStatus = "Account Created";
						printWriter.println(accountStatus);
						printWriter.println(account.getAccNumber());
						activeUser.add(username);
					}
					else {
						accountStatus = "User already exists, please log in";
						printWriter.println(accountStatus);
					}
					

					break;
				case "2":
					if (validationMap.containsKey(username) && userPwd.get(username).equals(password)) {
						if (!activeUser.contains(username)) {
							account = new Account(name, username, password, listOfAccNum);
							accountStatus = "Logged In successfully";
							printWriter.println(accountStatus);
							printWriter.println(validationMap.get(username));
						} else {
							accountStatus = "User is already logged In";
							printWriter.println(accountStatus);
						}
					} else {
						accountStatus = "Can't Log In, try again or create an account";
						printWriter.println(accountStatus);
					}
					break;
				}
				switch (accountStatus) {
				case "Account Created":
				case "Logged In successfully":
					while (true) {
						String operation = bufferedReader.readLine();
						String amount;
						String update;
						Operation amountHandling = new Operation(balanceSheet);
						switch (operation) {

						case "1":
							update = String.valueOf(amountHandling.checkBalance(validationMap.get(username)));
							printWriter.println(update);
							break;
						case "2":
							amount = bufferedReader.readLine();
							System.out.println(amount);
							if (validate.checkNumber(amount) && !amount.equals("")) {
								amountHandling.deposit(validationMap.get(username), amount);
								update = "Amount depositted";
								
							} else {
								System.out.println("reached");
								update = "Enter a valid amount";
							}
							printWriter.println(update);
							break;
						case "3":
							amount = bufferedReader.readLine();
							if ((validate.checkNumber(amount)) && !amount.equals("")) {
								if (balanceSheet.get(validationMap.get(username)) > Long.parseLong(amount)) {
									amountHandling.debit(validationMap.get(username), amount);
									update = "Amount debitted";
									printWriter.println(update);
								} else {
									update = "Insufficient Balance";
									printWriter.println(update);
								}
							} else {
								update = "Invalid input! Enter a number";
							}
							printWriter.println(update);
							break;
						case "4":
							String depositUser = bufferedReader.readLine();
							amount = bufferedReader.readLine();
							if ((validate.checkNumber(amount)) && !amount.equals("")) {
								if (balanceSheet.get(validationMap.get(username)) > Long.parseLong(amount)) {
									if (validationMap.containsKey(depositUser)) {
										amountHandling.transaction(validationMap.get(username), amount,
												validationMap.get(depositUser));
										update = "Transaction Successfull";
									} else {
										update = "User not present";
									}
								}else {
									update = "Insufficient Balance";
								}
							}else {
								update = ("Invalid input! Enter a number");
							}
							printWriter.println(update);

							break;
						case "5":
							activeUser.remove(username);
							update = "Quit";
							printWriter.println(update);
							break;

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Disconnected");
		} finally {

		}

	}
}
