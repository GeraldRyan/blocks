package com.gerald.ryan.blocks.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gerald.ryan.blocks.Service.TransactionService;
import com.gerald.ryan.blocks.Service.WalletService;
import com.gerald.ryan.blocks.entity.Transaction;
import com.gerald.ryan.blocks.entity.TransactionPool;
import com.gerald.ryan.blocks.entity.Wallet;
import com.gerald.ryan.blocks.initializors.Initializer;
import com.gerald.ryan.blocks.pubsub.PubNubApp;
import com.google.gson.Gson;
import com.pubnub.api.PubNubException;

@Controller
@RequestMapping("wallet")
@SessionAttributes({ "wallet", "latesttransaction", "pool", "username" })
public class WalletController {

	/*
	 * somewhat overloaded, the /transact page is for MAKING transactions, either
	 * via GET, which brings you to a form to submit, or POST, which returns JSON.
	 * Completing the form sends you to a /transaction GET page, and completes the
	 * transaction through requestparams (i.e. URL parameters) POSTing to
	 * /transaction
	 */

	PubNubApp pnapp = new PubNubApp();
	TransactionService tService = new TransactionService();
	TransactionPool pool = tService.getAllTransactionsAsTransactionPoolService();
	WalletService ws = new WalletService();

	public WalletController() throws InterruptedException {

	}

	@GetMapping("")
	public String getWallet(Model model) {
//		String username = (String) model.getAttribute("username");
//		Wallet w = ws.getWalletService(username);
		Wallet w = (Wallet) model.getAttribute("wallet");
		System.err.println("WHY IS WALLET NULL 66controller " + w.toString());
		w = ws.updateWalletBalanceService(w);
		model.addAttribute("wallet", w);
		return "wallet/wallet";
	}

	@GetMapping("/betterwallet")
	public String getBetterWallet(Model model) {

		return "wallet/betterwallet";
	}

	@GetMapping("/transact")
	public String getTransact(Model model)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		Wallet w = (Wallet) model.getAttribute("wallet");
		w = ws.getWalletService((String) model.getAttribute("username"));
		model.addAttribute("wallet", w);
		return "wallet/transact";
	}

	/**
	 * TODO collect transactions in transaction pool. Where to store? Store each
	 * transaction in database and the pool equals each one, just instantiate or
	 * rebuild pool on app startup Make a service.
	 * 
	 * Or else Make a transaction pool database that is one to many- really one to
	 * all. Is one to all a thing? I think just track transaction in separate DB
	 * 
	 * Solution- Transactions will be persisted to a transaction database. The
	 * transaction pool will be loaded in session memory on app startup by getting
	 * all transactions from database. Could use OneToMany, that way it is easily
	 * reconstructable as an object, otherwise you can just iterate yourself
	 * logically simpler way.
	 * 
	 * So need a Transaction Pool in session model. Don't need a transaction
	 * attribute as they are findable in model.
	 */

	@PostMapping("/transact")
	@ResponseBody
	public String postTransact(Model model, @RequestBody Map<String, Object> body) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException, IOException, InvalidAlgorithmParameterException {

		Wallet randomWallet = Wallet.createWallet("anon"); // simulate anon wallet on the wire

		Transaction nu = new Transaction(randomWallet, (String) body.get("address"),
				(double) ((Integer) body.get("amount")));
		pool = tService.getAllTransactionsAsTransactionPoolService();
		model.addAttribute("pool", pool);
		Transaction alt = pool.findExistingTransactionByWallet(nu.getSenderAddress());
		if (alt == null) {
			System.err.println("Transaction 2 is null. there is no existing transaction of that sender"
					+ nu.getSenderAddress() + "==" + randomWallet.getAddress());
			model.addAttribute("latesttransaction", nu);
			tService.addTransactionService(nu);
//			broadcastTransaction(nu);  // switch on and off as desired
			return nu.toJSONtheTransaction();
		} else {
			System.out.println("Existing transaction found!");
			Transaction updated = tService.updateTransactionService(nu, alt);
			model.addAttribute("latesttransaction", updated);
//			broadcastTransaction(updated); // switch on and off as desired
			return updated.toJSONtheTransaction();
		}
	}

	@RequestMapping(value = "/transaction", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String postTransaction(@ModelAttribute("wallet") Wallet w, Model model,
			@RequestParam("address") String address, @RequestParam("amount") double amount, HttpServletRequest request)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		Transaction nu = new Transaction(w, address, amount);
		pool = tService.getAllTransactionsAsTransactionPoolService();
		Transaction alt = pool.findExistingTransactionByWallet(nu.getSenderAddress());
		if (alt == null) {
			System.err.println("Transaction 2 is null. there is no existing transaction of that sender"
					+ nu.getSenderAddress() + "==" + w.getAddress());
			model.addAttribute("latesttransaction", nu);
			tService.addTransactionService(nu);
			if (false) {
				broadcastTransaction(nu); // TODO set up global variable to turn on and off broadcasting
			}
			return nu.toJSONtheTransaction();
		} else {
			System.out.println("Existing transaction found!");
			Transaction updated = tService.updateTransactionService(nu, alt);
			model.addAttribute("latesttransaction", updated);
			if (false) {
				broadcastTransaction(updated); // TODO set up global variable to turn on and off broadcasting
			}
			return updated.toJSONtheTransaction();
		}
	}

	@PostMapping("/transactt")
	@ResponseBody
	public String postDummyTransactions(Model model, @RequestBody Map<String, Object> body) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException, IOException, InvalidAlgorithmParameterException {

		String numString = (String) body.get("number");
		System.err.println("MAKING TRANSACTION FROM TRANSACTT");
		System.err.println("MAKING TRANSACTION FROM TRANSACTT");
		System.err.println("MAKING TRANSACTION FROM TRANSACTT");
		System.err.println("MAKING TRANSACTION FROM TRANSACTT");
		List<Transaction> list = new ArrayList();
		int n = 1;
		if (numString != null) {
			n = new Random().nextInt(999);
		}
		String fromaddress = (String) body.get("fromaddress");
		if (fromaddress == null) {
			list = new Initializer().postNTransactions(n);
		} else {
			list = new Initializer().postNTransactions(n, fromaddress);
		}

		pool = tService.getAllTransactionsAsTransactionPoolService();
		model.addAttribute("pool", pool);
		return new Gson().toJson(list);
	}

	public void broadcastTransaction(Transaction t) {
		try {
			pnapp.broadcastTransaction(t);
		} catch (PubNubException e) {
			System.err.println("Problem broadcasting the transaction.");
			e.printStackTrace();
		}
	}

}
