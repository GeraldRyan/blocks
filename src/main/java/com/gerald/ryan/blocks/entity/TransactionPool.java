/**
 * 
 */
package com.gerald.ryan.blocks.entity;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.mapping.Map;

import com.google.gson.Gson;

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

	public static TransactionPool fillTransactionPool(List<Transaction> transactionList) {
		TransactionPool tp = new TransactionPool();
		for (Transaction t : transactionList) {
			t.rebuildOutputInput();
			tp.putTransaction(t);
		}
		return tp;
	}


	/**
	 * Finds existing transaction of given Wallet if exists in pool, otherwise
	 * returns null
	 * 
	 * @return
	 */
	public Transaction findExistingTransactionByWallet(String walletAddress) {
		if (this.getTransactionMap().keySet().size() == 0) {
			System.err.println("KEYSET IS SIZE 00000000");
			return null;
		}
		HashMap<String, Object> tmpinput;
		for (String uuid : this.getTransactionMap().keySet()) {
			System.err.println("UUID OF TRANSACTION IS " + uuid);
			Transaction t = (Transaction) this.getTransactionMap().get(uuid);
			System.err.println("INPUT JSON");
			System.err.println(t.getInputjson());
			System.err.println("OUTPUT JSON");
			System.err.println(t.getOutputjson());
			tmpinput = new Gson().fromJson(t.getInputjson(), HashMap.class);
			if (tmpinput.get("address").equals(walletAddress)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Return the transactions of the transaction pool as a string that is suited to
	 * add to block data field, fit for mining, represented in json serialized form
	 * 
	 * @return
	 */
	public String getMinableTransactionDataString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
//		String transactionData = "[";
		this.transactionMap.forEach((uuid, t) -> {
			sb.append(((Transaction) t).__repr__());
			sb.append(",");
		});
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");
		System.out.println(sb.toString());
		return sb.toString().replace("\\\\", "");

	}

	/**
	 * Main data payload of this class- the transaction map contains a HashMap of
	 * all the transactions in this pool
	 * 
	 * @return
	 */
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
