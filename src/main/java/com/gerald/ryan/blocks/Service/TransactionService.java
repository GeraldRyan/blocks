package com.gerald.ryan.blocks.Service;

import com.gerald.ryan.blocks.Dao.TransactionDao;
import com.gerald.ryan.blocks.entity.Transaction;
import com.gerald.ryan.blocks.entity.TransactionPool;

public class TransactionService {
	TransactionDao transactionD = new TransactionDao();

	/**
	 * Gets a transaction from the local database by transaction ID
	 * 
	 * @param uuid
	 * @return
	 */
	public Transaction getTransactionService(String uuid) {
		return transactionD.getTransaction(uuid);
	}

	/**
	 * Adds a transaction to the database
	 * 
	 * @param t
	 * @return
	 */
	public Transaction addTransactionService(Transaction t) {
		return transactionD.addTransaction(t);
	}

	public Transaction updateTransactionService(Transaction t1, Transaction t2) {
		return transactionD.updateTransaction(t1, t2);
	}

	public Transaction removeTransactionService(String UUID) {
		return transactionD.removeTransaction(UUID);
	}

	/**
	 * Gets entire list of transactions as TransactionPool type from database
	 * 
	 * @return
	 */
	public TransactionPool getAllTransactionsAsTransactionPoolService() {
		return transactionD.getAllTransactionsAsTransactionPool();
	}
}
