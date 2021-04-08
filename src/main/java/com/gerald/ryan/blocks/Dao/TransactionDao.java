package com.gerald.ryan.blocks.Dao;

import java.util.List;

import com.gerald.ryan.blocks.dbConnection.DBConnection;
import com.gerald.ryan.blocks.entity.Transaction;
import com.gerald.ryan.blocks.entity.TransactionPool;



public class TransactionDao extends DBConnection implements TransactionDaoI {

	@Override
	public Transaction getTransaction(String uuid) {
		// TODO Auto-generated method stub
		// might not be needed
		return null;
	}

	@Override
	public Transaction addTransaction(Transaction t) {
		this.connect();
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		this.disconnect();
		return t;
	}

	@Override
	public Transaction updateTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction removeTransaction(String UUID) {
		this.connect();
		Transaction t = em.find(Transaction.class, UUID);
		em.remove(t);
		this.disconnect();
		return t;
	}

	@Override
	public TransactionPool getAllTransactionsAsTransactionPool() {
		TransactionPool pool = new TransactionPool();
		List<Transaction> resultsList = em.createQuery("SELECT t FROM TRANSACTION t").getResultList();
		for (Transaction t : resultsList) {
			pool.putTransaction(t);
		}
		return pool;
	}

}
