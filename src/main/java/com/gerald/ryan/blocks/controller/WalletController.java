package com.gerald.ryan.blocks.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

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
import com.gerald.ryan.blocks.entity.Transaction;
import com.gerald.ryan.blocks.entity.TransactionPool;
import com.gerald.ryan.blocks.entity.Wallet;
import com.gerald.ryan.blocks.pubsub.PubNubApp;
import com.pubnub.api.PubNubException;

@Controller
@RequestMapping("wallet")
@SessionAttributes({ "wallet", "latesttransaction" })
public class WalletController {

	PubNubApp pnapp = new PubNubApp();
	TransactionService tService = new TransactionService();
	TransactionPool pool = tService.getAllTransactionsAsTransactionPoolService();

	public WalletController() throws InterruptedException {

	}

	/**
	 * Preload site with wallet object
	 * 
	 * TODO - GET THIS WALLET FROM DATABASE. NULL IF NULL. OPTION TO CREATE, AND
	 * THEN TO PERSIST BOTH TO DATABASE AND SESSION
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidAlgorithmParameterException
	 */
	@ModelAttribute("wallet")
	public Wallet addWallet()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		Wallet wallet = Wallet.createWallet();
		return wallet;
	}

	@GetMapping("")
	public String getWallet(Model model) {

		return "wallet/wallet";
	}

	@GetMapping("/transact")
	public String getTransact(@ModelAttribute("wallet") Wallet w)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
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

		Wallet randomWallet = Wallet.createWallet(); // simulate random wallet on the wire, not saved in memory
		// in future find way to overload this message with option

		Transaction t1 = new Transaction(randomWallet, (String) body.get("address"),
				(double) ((Integer) body.get("amount")));
		tService.addTransactionService(t1); // error here
		try {
			pnapp.broadcastTransaction(t1);
		} catch (PubNubException e) {
			System.err.println("Problem broadcasting the transaction.");
			e.printStackTrace();
		}
		return t1.toJSONtheTransaction();
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
			return nu.toJSONtheTransaction();
		} else {
			System.out.println("Existing transaction found!");
			Transaction updated = tService.updateTransactionService(nu, alt);
			model.addAttribute("latesttransaction", updated);
			return updated.toJSONtheTransaction();
		}
		// if transaction found in pool
		// update service

		// else

	}

}
