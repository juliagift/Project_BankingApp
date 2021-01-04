package com.bagonationalbank.app.service.impl;

import java.util.List;

import com.bagonationalbank.app.dao.BankingOperationsDAO;
import com.bagonationalbank.app.dao.impl.BankingOperationsDAOImpl;
import com.bagonationalbank.app.exception.BusinessException;
import com.bagonationalbank.app.model.Account;
import com.bagonationalbank.app.model.Customer;
import com.bagonationalbank.app.model.Pin;
import com.bagonationalbank.app.model.Transactions;
import com.bagonationalbank.app.service.BankingOperationsService;


public class BankingOperationsServiceImpl implements BankingOperationsService{
	
	private BankingOperationsDAO bankingOperationsDAO = new BankingOperationsDAOImpl();

	@Override
	public String createNewAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer logIn(Pin customerCredentials) throws BusinessException {
		Customer customer = bankingOperationsDAO.logIn(customerCredentials);
		return customer;
	}

	@Override
	public double viewBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double withdrawFunds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double depositFunds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double transferFunds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAllAccounts(Customer logIn) throws BusinessException {
		List<Account> allAccountsList = null;
		allAccountsList = bankingOperationsDAO.getAllAccounts(logIn);
		return allAccountsList;
	}

	@Override
	public List<Transactions> getAllTransactions() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
