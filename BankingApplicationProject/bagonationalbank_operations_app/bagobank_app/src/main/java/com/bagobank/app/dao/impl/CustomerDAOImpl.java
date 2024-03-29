package com.bagobank.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bagobank.app.dao.CustomerDAO;
import com.bagobank.app.dao.dbutil.PostgresqlConnection;
import com.bagobank.app.exception.BusinessException;
import com.bagobank.app.model.Account;
import com.bagobank.app.model.Customer;
import com.bagobank.app.model.Transaction;






public class CustomerDAOImpl implements CustomerDAO {
	
	private static Logger log = Logger.getLogger(CustomerDAOImpl.class);
	private Account account;

	@Override
	public int createCustomer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateCustomer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Account> getAllAccounts(Customer customer) throws BusinessException {
		List<Account> accountsList = new ArrayList<>();
		
		try (Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select account_id, account_type, balance from bago_national_bank.account where customer_id = ? and status = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customer.getCustomerId());
			preparedStatement.setString(2, "active");
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Account account = new Account();
				account.setAccountId(resultSet.getInt("account_id"));
				account.setAccountType(resultSet.getString("account_type"));
				account.setBalance(resultSet.getFloat("balance"));
				accountsList.add(account);				
			}
			
			if (accountsList.size() == 0) {
				throw new BusinessException("No Accounts found with Account ID: "+resultSet.getInt("account_id"));  
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			log.info(e); //Take off this line when app is live
			throw new BusinessException("Internal error. Contact SYSADMIN! getAllAccounts");
		}
		
		return accountsList;
	}

	@Override
	public List<Transaction> getTransactionsByAccountId(Account account) throws BusinessException {
		List<Transaction> transactionsList = new ArrayList<>();
		try (Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select transaction_id, transaction_type, amount, old_balance, new_balance, transaction_date, account_id, customer_id "
					+ "from bago_national_bank.transactions where account_id = ? order by transaction_date";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, account.getAccountId());
			
			ResultSet resultSet = preparedStatement.executeQuery();
			Account retrievedAccount = null;
			Customer retrievedCustomer = null;
			
			while (resultSet.next()) {
				Transaction transaction = new Transaction();
				transaction.setTransactionId(resultSet.getInt("transaction_id"));
				transaction.setTransactionType(resultSet.getString("transaction_type"));
				transaction.setAmount(resultSet.getDouble("amount"));
				transaction.setOldBalance(resultSet.getDouble("old_balance"));
				transaction.setNewBalance(resultSet.getDouble("new_balance"));
				transaction.setTransactionDate(resultSet.getDate("transaction_date"));
				retrievedAccount = new Account(resultSet.getInt("account_id"));
				transaction.setAccount(retrievedAccount);
				retrievedCustomer = new Customer(resultSet.getInt("customer_id"));
				transaction.setCustomer(retrievedCustomer);
				transactionsList.add(transaction);
			} 
			
			if (transactionsList.size() == 0) {
				throw new BusinessException("\nNo Transactions associated with Account ID: " +account.getAccountId());
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			log.info(e.getMessage());  //remove when app is live
			throw new BusinessException("Internal error. Contact SYSADMIN! getTransactionsByAccountId");
		}
		
		return transactionsList;
	}

	@Override
	public List<Transaction> getTransactionsByCustomerId(Customer customer) throws BusinessException {
		List<Transaction> transactionsList = new ArrayList<>();
		Transaction transaction = null; 
		
		try (Connection connection = PostgresqlConnection.getConnection()) {
			//String sql = "select transaction_id, transaction_type, amount, old_balance, new_balance, transaction_date, account_id, customer_id "
				//	+ "from bago_national_bank.transactions where customer_id = ? order by transaction_date";
			
			String sql = "select t.transaction_id, t.transaction_type, t.amount, t.old_balance, t.new_balance, t.transaction_date, t.account_id, t.customer_id, a.account_type "
				+ "from bago_national_bank.transactions t "
				+ "join bago_national_bank.account a on t.customer_id = a.customer_id "
				+ "where t.customer_id = ? order by transaction_date";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customer.getCustomerId());
			
			ResultSet resultSet = preparedStatement.executeQuery();
			Account retrievedAccount = null;
			Customer retrievedCustomer = null;
			
			while (resultSet.next()) {
				transaction = new Transaction();
				transaction.setTransactionId(resultSet.getInt("transaction_id"));
				transaction.setTransactionType(resultSet.getString("transaction_type"));
				transaction.setAmount(resultSet.getDouble("amount"));
				transaction.setOldBalance(resultSet.getDouble("old_balance"));
				transaction.setNewBalance(resultSet.getDouble("new_balance"));
				transaction.setTransactionDate(resultSet.getDate("transaction_date"));
				
				retrievedAccount = new Account(resultSet.getInt("account_id"));
				retrievedAccount.setAccountType(resultSet.getString("account_type"));
				transaction.setAccount(retrievedAccount);
				retrievedCustomer = new Customer(resultSet.getInt("customer_id"));
				transaction.setCustomer(retrievedCustomer);
				transactionsList.add(transaction);
			} 
			if (transactionsList.size() == 0) {
				throw new BusinessException("\nNo Transactions associated with Customer ID: " +customer.getCustomerId());
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			log.info(e.getMessage());  //remove when app is live
			throw new BusinessException("Internal error. Contact SYSADMIN! getTransactionsByCustomerId");
		}
		
		return transactionsList;
	}

	@Override
	public List<Transaction> getAllTransactions() throws BusinessException {
		List<Transaction> transactionsList = new ArrayList<>();
		
		try (Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select t.transaction_id, t.transaction_type, t.amount, t.old_balance, t.new_balance, t.transaction_date, t.account_id, t.customer_id "
					+ "from bago_national_bank.transactions t join  bago_national_bank.account a on a.customer_id = t.customer_id where status = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "active");
			
			ResultSet resultSet = preparedStatement.executeQuery();
			Account retrievedAccount = null;
			Customer retrievedCustomer = null;
			
			while (resultSet.next()) {
				Transaction transaction = new Transaction();
				transaction.setTransactionId(resultSet.getInt("transaction_id"));
				transaction.setTransactionType(resultSet.getString("transaction_type"));
				transaction.setAmount(resultSet.getDouble("amount"));
				transaction.setOldBalance(resultSet.getDouble("old_balance"));
				transaction.setNewBalance(resultSet.getDouble("new_balance"));
				transaction.setTransactionDate(resultSet.getDate("transaction_date"));
				retrievedAccount = new Account(resultSet.getInt("account_id"));
				transaction.setAccount(retrievedAccount);
				retrievedCustomer = new Customer(resultSet.getInt("customer_id"));
				transaction.setCustomer(retrievedCustomer);
				transactionsList.add(transaction);
			}
			
			if (transactionsList.size() == 0) {
				throw new BusinessException("No Transactions found in the DB");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error. Contact SYSADMIN! getAllTransactionss");
		}
		return transactionsList;
	}

}
