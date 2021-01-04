package com.bagonationalbank.app.dao;

import java.util.List;

import com.bagonationalbank.app.exception.BusinessException;
import com.bagonationalbank.app.model.Account;
import com.bagonationalbank.app.model.Customer;
import com.bagonationalbank.app.model.Pin;
import com.bagonationalbank.app.model.Transactions;

public interface BankingOperationsDAO {

	public String createNewAccount();
	public Customer logIn(Pin pin) throws BusinessException;
	public double viewBalance();
	public double withdrawFunds();
	public double depositFunds();
	public double transferFunds();
	public List<Customer> getAllCustomers();
	public List<Account> getAllAccounts(Customer logIn) throws BusinessException;
	public List<Transactions> getAllTransactions();
	
}
