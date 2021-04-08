/**
 * 
 */
package com.gerald.ryan.blocks.entity;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;

/**
 * @author Gerald Ryan Natural constructor is zero arg
 *
 */
public class TransactionPool {

	HashMap<String, Object> transactionMap = new HashMap<String, Object>();

	public TransactionPool() {

	}

	/**
	 * Set a transaction in this pool
	 * 
	 * @param transaction
	 * @return
	 */
	public Transaction putTransaction(Transaction transaction) {
		transactionMap.put(transaction.getUuid(), transaction);
		return transaction;
	}

	public void syncTransactionPool() {

	}

	public void broadcastTransactionPool() {

	}

	public void TransactionPool() {

	}

	/**
	 * Finds existing transaction of given Wallet if exists in pool, otherwise
	 * returns null
	 * 
	 * @return
	 */
	public Transaction findExistingTransactionByWallet(String walletAddress) {
		for (String transkey : this.getTransactionMap().keySet()) {
			Transaction t = (Transaction) this.getTransactionMap().get(transkey);
			if (t.getInput().get("address") == walletAddress) {
				return t;
			}
		}
		return null;
//		return (Transaction) this.getTransactionMap().get(uuid);
	}

	public HashMap<String, Object> getTransactionMap() {
		return transactionMap;
	}

	public void consoleLogAll() {
		System.err.println("Transactions in Transaction Pool");
		for (String id : this.getTransactionMap().keySet()) {
			System.out.println("key: " + id + " value: " + this.getTransactionMap().get(id));
		}
	}

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, IOException, InvalidAlgorithmParameterException {
		TransactionPool pool = new TransactionPool();
		Wallet w = Wallet.createWallet();
		Wallet unusedWallet = Wallet.createWallet();
		pool.putTransaction(new Transaction(w, "foo", 15));
		pool.putTransaction(new Transaction(Wallet.createWallet(), "foo", 15));
		pool.putTransaction(new Transaction(Wallet.createWallet(), "foo", 15));
		pool.putTransaction(new Transaction(Wallet.createWallet(), "foo", 15));
		pool.putTransaction(new Transaction(Wallet.createWallet(), "foo", 15));
		Transaction t = pool.findExistingTransactionByWallet(w.getAddress());
		System.out.println(t); // expect a string representation of object (or address in memory). Indeed
		Transaction tnull = pool.findExistingTransactionByWallet(unusedWallet.getAddress());
		System.out.println(tnull); // expect null. Indeed

	}
}
