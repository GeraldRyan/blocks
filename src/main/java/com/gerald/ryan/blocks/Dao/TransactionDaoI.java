package com.gerald.ryan.blocks.Dao;

import java.util.List;

import com.gerald.ryan.blocks.entity.Transaction;
import com.gerald.ryan.blocks.entity.TransactionPool;

public interface TransactionDaoI {

	public Transaction getTransaction(String uuid);

	public Transaction addTransaction(Transaction t);

	public Transaction updateTransaction(Transaction t1, Transaction t2);

	public Transaction removeTransaction(String UUID);

	public TransactionPool getAllTransactionsAsTransactionPool();

	public List<Transaction> getAllTransactionsAsTransactionList();

}
