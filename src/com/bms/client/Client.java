package com.bms.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.bms.validate.Validate;

public class Client {

	public static void main(String[] args) {

		try {
			Socket socket = new Socket("localhost", 8000);
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream, true);
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader serverResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (true) {
				System.out.println("Press any number: \n1: Create an account \n2: Log In");
				System.out.println("----------------------------------------------------------");
				String choice = userInput.readLine();
				String name;
				String username;
				String pwd;
				switch (choice) {
				case "1":
					System.out.println("Enter your name: (Numbers are not allowed)");
					name = userInput.readLine();
					System.out
							.println("Enter your username: (It should be  unique.Numbers and spaces are not allowed.)");
					username = userInput.readLine();
					System.out.println("Enter your password: (Spaces are not allowed and minimum length should be 4)");
					pwd = userInput.readLine();
					break;
				case "2":
					System.out.println("Enter your name: (Numbers and spaces are not allowed)");
					name = userInput.readLine();
					System.out.println("Enter your username: (It should be unique.Numbers and spaces are not allowed)");
					username = userInput.readLine();
					System.out.println("Enter your password: (Spaces are not allowed and minimum length should be 4)");
					pwd = userInput.readLine();
					break;
				default:
					System.out.println("Invalid choice");
					continue;
				}

				Validate validate = new Validate();
				String credentials = validate.checkCredentials(name, username, pwd);
				if (credentials.equals("Credentials approved")) {
					System.out.println(credentials);
				} else {
					System.out.println(credentials);
					continue;
				}

				printWriter.println(choice);
				printWriter.println(name);
				printWriter.println(username);
				printWriter.println(pwd);

				String response = serverResponse.readLine();
				System.out.println("*************************");
				System.out.println(response);
				System.out.println("*************************");

				
				String update;
				switch (response) {
				case "Account Created":
				case "Logged In successfully":
					System.out.println("Your account number is: " + serverResponse.readLine());
					boolean running = true;
					while (running) {
						System.out.println(
								"Press any number: \n1: Check balance \n2: Deposit amount \n3: Withdraw some amount \n4: Transactional Deposit \n5: Quit");
						System.out.println("----------------------------------------------------------");
						String option = userInput.readLine();
						String amount;
						switch (option) {
						case "1":
							printWriter.println(option);
							printWriter.flush();
							break;
						case "2":
							System.out.println("Enter deposit amount");
							amount = new String(userInput.readLine());

							printWriter.println(option);
							printWriter.println(amount);
							printWriter.flush();
							break;

						case "3":
							System.out.println("Enter withdrawal amount");
							amount = new String(userInput.readLine());
							printWriter.println(option);
							printWriter.println(amount);
							printWriter.flush();
							break;
						case "4":
							System.out.println("Enter deposit account holder username");
							String otherAccUser = new String(userInput.readLine());
							System.out.println("Enter transactional amount");
							amount = new String(userInput.readLine());
							printWriter.println(option);
							printWriter.println(otherAccUser);
							printWriter.println(amount);
							printWriter.flush();
							break;
						case "5":
							printWriter.println(option);
							running = false;
							socket.close();
							break;
						default:
							System.out.println("Invalid choice ! Enter a number");
							continue;
						}

						update = new String(serverResponse.readLine());
						System.out.println(update);
					}
					break;
				default:
					break;
				}

			}

		} catch (IOException e) {
			System.out.println("Something went wrong ! Please try again.");
		}
	}

}
