package com.bagonationalbank.app.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bagonationalbank.app.exception.BusinessException;
import com.bagonationalbank.app.model.Account;
import com.bagonationalbank.app.model.Customer;
import com.bagonationalbank.app.model.Pin;
import com.bagonationalbank.app.model.Username;
import com.bagonationalbank.app.service.BankingOperationsService;
import com.bagonationalbank.app.service.impl.BankingOperationsServiceImpl;

public class ConsoleMenu {

	private static Logger log = Logger.getLogger(ConsoleMenuMain.class);
	Scanner sc = new Scanner(System.in);
	private BankingOperationsService bankingOperationsService = new BankingOperationsServiceImpl();
	
	public int startMenu() {
		
		
		int choice = 0;
		
		do {
			log.info("");
			log.info("****************************************************");
			log.info(" Welcome To Bago National Bank's Online Banking App ");
			log.info("****************************************************");
			log.info("\nBANKING MENU");
			log.info("-----------------");
			log.info("1) Returning Customer");
			log.info("2) New Customer");
			log.info("3) Employee");
			log.info("4) EXIT");
			log.info("Please enter an appropriate choice between 1-4");
			
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				
			}
			
			if (choice >= 1 && choice <5) {
				return choice;
			} else {
				log.info("INVALID MENU OPTION.... Kindly retry!!!!!");
			}
			
			
		} while (true);
	}
	
	
	public Pin logInMenu(int user) {
		if (user == 1) {
			log.info("");
			log.info("RETURNING CUSTOMER");
			log.info("---------------------");
		} 
		else if (user == 2) {
			log.info("Welcome Employee");
		}
		
		String usernameInput = null;
		String pinInput = null;
		
		log.info("Please Enter your Username");
		usernameInput = sc.nextLine();
		
		log.info("\nPlease Enter your Pin");
		pinInput = sc.nextLine();
		
		Username username = new Username(usernameInput);
		Pin pin = new Pin(pinInput, username);
		
		return pin;
		
	}
	
	public int getCustomerMenu (Customer logIn) {
		int choice = 0;
		//log.info("\nLOGIN SUCCESSFUL!!!"); 
		log.info("\nWELCOME "+logIn.getFirstName()+ " " +logIn.getLastName() +"!");
		log.info("=*=*=*=*=*=*=*=*=*=*=*=*");
		
		do {
	
			log.info("");
			log.info("What would you like to do today?");
			log.info("--------------------------------");
			log.info("1) View Acount Balance");
			log.info("2) Deposit Funds");
			log.info("3) Withdraw Funds");
			log.info("4) Transfer Funds");
			log.info("5) EXIT");
			log.info("Please enter an appropriate choice between 1-5");
			
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				
			}
			
			if (choice >= 1 && choice < 6) {
				return choice;
			} else {
				log.info("INVALID MENU OPTION.... Kindly retry!!!!!");
			}
		
		} while (true);
		
		
	}
	
	public void getAllAccounts (Customer logIn) throws BusinessException {
		try {
			List<Account> allAccountsList = bankingOperationsService.getAllAccounts(logIn);
			
			if (allAccountsList != null && allAccountsList.size() > 0) {
				log.info("");
				log.info("ACCOUNT DETAILS");
				log.info("------------------");
				
				for (Account a :allAccountsList) {
					
					log.info("Account ID: "+a.getAccountId() +" ** Type: "+a.getType() +" ** Balance: $"+ String.format("%.2f", a.getBalance()));
				}
			}
			
		} catch (BusinessException e) {
			log.info(e.getMessage());
		}
		return;
	}
	
	public void depositFunds( Account accounts, double amount) {
		log.info("Please enter Account ID : ");  //get account number
		
		//search accounts
		log.info("Please Enter Amount to Deposit: ");   //amount
		double balance = 0;
		if (amount != 0) {
			balance = balance + amount;
		} else {
			log.info("Cannot deposit a negative amount");
		}
		
	}
	
	public void withdrawFunds( Account accounts, double amount) {
		log.info("Please enter Account ID : ");
		
		log.info("Please Enter Amount to Deposit: ");
		
	}
	
	public void transferFunds( Account accounts, double amount) {
		log.info("Please enter Account ID : ");
		
		log.info("Please Enter Amount to Transfer: ");
		
	}
	
	public int getAccountType() {
		int choice = 0;
		
		do {
			
			log.info("PLEASE SELECT ACCOUNT TYPE");
			log.info("------------------------------");
			log.info("1) Checking");
			log.info("2) Saving");
			log.info("3) EXIT");
			log.info("Please enter an appropriate choice between 1-3");
			
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				
			}
			
			if (choice >= 1 && choice < 4) {
				return choice;
			} else {
				log.info("INVALID MENU OPTION.... Kindly retry!!!!!");
			}
			
			
		}while (true);
		
		
	}
	
}
