package com.bms.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Server {

	static Map<String, Map<String, String>> accountMap = new HashMap<String, Map<String, String>>(); // {username:
	static Map<String, String> validationMap = new HashMap<String, String>(); // {username: accNum}
	static Map<String, Long> balanceSheet = new HashMap<String, Long>(); // {accNumber: amount}
	static List<String> listOfAccNum = new ArrayList<String>(); // list of accNums to get unique nums
	static Set<String> activeUser = new HashSet<String>(); // list of active users : username
	static Map<String, String> userPwd = new HashMap<String, String>(); // {username: pwd}
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8000);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				ThreadHandler threadHandler = new ThreadHandler(validationMap, balanceSheet, listOfAccNum, activeUser,
						userPwd, accountMap, clientSocket);
				Thread thread = new Thread(threadHandler);
				thread.start();

				// Create a new client handler thread for the new client
			}
		} catch (IOException e) {
			System.err.println("Server error: " + e.getMessage());
		} 
		
	}

}
