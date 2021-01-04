package com.bagonationalbank.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bagonationalbank.app.dao.BankingOperationsDAO;
import com.bagonationalbank.app.dao.dbutil.PostgresqlConnection;
import com.bagonationalbank.app.exception.BusinessException;
import com.bagonationalbank.app.main.ConsoleMenuMain;
import com.bagonationalbank.app.model.Account;
import com.bagonationalbank.app.model.Customer;
import com.bagonationalbank.app.model.Pin;
import com.bagonationalbank.app.model.Transactions;
import com.bagonationalbank.app.service.BankingOperationsService;


public class BankingOperationsDAOImpl implements BankingOperationsDAO{
	
	private static Logger log = Logger.getLogger(BankingOperationsDAOImpl.class);
	
	
	@Override
	public String createNewAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer logIn(Pin customerCredentials) throws BusinessException {
		Customer customerLogIn = null;
		
		try (Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select u.username, u.customer_id, p.pin, c.first_name, c.last_name "
					+ "from bago_national_bank.username u "
					+ "join bago_national_bank.pin p on u.username_id = p.username_id "
					+ "join bago_national_bank.customer c on u.customer_id = c.customer_id where u.username = ? and p.pin = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, customerCredentials.getUsername().getUsername());
			preparedStatement.setString(2, customerCredentials.getPin());
			
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				customerLogIn = new Customer();
				customerLogIn.setCustomerId(resultSet.getInt("customer_id"));
				customerLogIn.setFirstName(resultSet.getString("first_name"));
				customerLogIn.setLastName(resultSet.getString("last_name"));
			} else {
				throw new BusinessException("\nNo Customer found with Username: "+customerCredentials.getUsername().getUsername()+ " and Pin: "+customerCredentials.getPin());
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			log.info("exception in DAO");
			log.info(e);    //Take off this line when app is live
			throw new BusinessException("Internal error occurred. Contact Admin!!!");
		}
				
				
		return customerLogIn;
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
		List<Account> accountsList = new ArrayList<>();
		
		try (Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select account_id, account_type, balance from bago_national_bank.account where customer_id = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, logIn.getCustomerId());
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Account account = new Account();
				account.setAccountId(resultSet.getInt("account_id"));
				account.setType(resultSet.getString("account_type"));
				account.setBalance(resultSet.getFloat("balance"));
				accountsList.add(account);				
			}
			
			if (accountsList.size() == 0) {
				throw new BusinessException("You have no Accounts");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			log.info(e);
			throw new BusinessException("Internal error. Contact SYSADMIN!");
		}
		
		return accountsList;
	}

	@Override
	public List<Transactions> getAllTransactions() {
		// TODO Auto-generated method stub
		return null;
	}


}
