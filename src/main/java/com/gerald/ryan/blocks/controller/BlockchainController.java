package com.gerald.ryan.blocks.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gerald.ryan.blocks.Service.BlockService;
import com.gerald.ryan.blocks.Service.BlockchainService;
import com.gerald.ryan.blocks.Service.TransactionService;
import com.gerald.ryan.blocks.entity.Block;
import com.gerald.ryan.blocks.entity.Blockchain;
import com.gerald.ryan.blocks.entity.Transaction;
import com.gerald.ryan.blocks.entity.TransactionPool;
import com.gerald.ryan.blocks.exceptions.BlocksInChainInvalidException;
import com.gerald.ryan.blocks.exceptions.ChainTooShortException;
import com.gerald.ryan.blocks.exceptions.GenesisBlockInvalidException;
import com.gerald.ryan.blocks.initializors.Initializer;
import com.gerald.ryan.blocks.pubsub.PubNubApp;
import com.gerald.ryan.blocks.utilities.TransactionRepr;
import com.google.gson.JsonElement;
import com.pubnub.api.PubNubException;

@Controller
@SessionAttributes({ "blockchain", "minedblock", "wallet" })
@RequestMapping("blockchain")
public class BlockchainController {

	TransactionService tService = new TransactionService();
	TransactionPool pool = tService.getAllTransactionsAsTransactionPoolService();

	public BlockchainController() throws InterruptedException {
	}

	BlockService blockApp = new BlockService();
	BlockchainService blockchainApp = new BlockchainService();
	PubNubApp pnapp = new PubNubApp();

	/**
	 * Pulls up beancoin blockchain on startup.
	 * 
	 * If no beancoin exists, create one and populate it with initial values
	 * 
	 * Also syncs blockchain so should be updated
	 */
	@ModelAttribute("blockchain")
	public Blockchain addBlockchain(Model model) throws NoSuchAlgorithmException, InterruptedException {
//		System.err.println("addBlockchain called at controller");
		try {
			Blockchain blockchain = blockchainApp.getBlockchainService("beancoin");
//			PubNubApp pnapp = new PubNubApp(blockchain, (TransactionPool) model.getAttribute("transactionpool"));
			System.out.println("Pulling up your beancoin from our records");
			return blockchain;
		} catch (Exception e) {
			System.err.println("Creating new beancoin");
			Blockchain blockchain = blockchainApp.newBlockchainService("beancoin");
			Initializer.loadBC("beancoin");
			Blockchain populated_blockchain = blockchainApp.getBlockchainService("beancoin");
			return populated_blockchain;
		}
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String serveBlockchain(Model model) throws NoSuchAlgorithmException, InterruptedException,
			ChainTooShortException, GenesisBlockInvalidException, BlocksInChainInvalidException {
		refreshChain(model);
		return ((Blockchain) model.getAttribute("blockchain")).toJSONtheChain();
	}

	@RequestMapping(value = "mine", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getMine(@ModelAttribute("blockchain") Blockchain blockchain, Model model)
			throws NoSuchAlgorithmException, PubNubException, InterruptedException {
		pool = tService.getAllTransactionsAsTransactionPoolService(); 
		if (pool.getMinableTransactionDataString() == null) {
			return "No data to mine. Tell your friends to make transactions";
		}
		String transactionData = "MAIN INSTANCE STUBBED DATA"; // overruled -->>
		transactionData = pool.getMinableTransactionDataString();
		List<Transaction> tlist = tService.getAllTransactionsAsTransactionList();
		Block new_block = blockchainApp.addBlockService("beancoin", transactionData);
		if (false) { // CONFIGURE BROADCASTING VARIABLE TURN BACK ON LATER
		pnapp.broadcastBlock(new_block); 			
		}
		model.addAttribute("minedblock", new_block);
		blockchain = blockchainApp.getBlockchainService("beancoin"); // refresh. it's all about refreshing and syncing.
																		// sync with db
		model.addAttribute("blockchain", blockchain); // and refresh in memeory.
		pool.refreshBlockchainTransactionPool(blockchain);
//		pool = tService.getAllTransactionsAsTransactionPoolService(); // After potentially deleting, refresh, but should be necessary only on page load (lazy concept)
		return new_block.webworthyJson(tlist); // This pollutes with escape chars \ because string
	}

	public void refreshChain(Model model) {
		System.err.println("Refreshing Blockchain from local database");
		Blockchain newer_blockchain_from_db = blockchainApp.getBlockchainService("beancoin");
		try {
			// Database for some reason loads ArrayList<Block> unsorted. Manually resorting
			// it here upon load.
			ArrayList<Block> new_chain = new ArrayList<Block>(newer_blockchain_from_db.getChain());
			System.out.println("RE-SORTING ArrayList<Block>");
			// I believe I have to make a new chain instance for mutation. Won't mutate
			// Blockchain.chain property?
			Collections.sort(new_chain, Comparator.comparingLong(Block::getTimestamp));
			/*
			 * Are setter methods secure for chain replacement? Could replace with invalid
			 * chain. Should use replaceChain method, incorporating this into that, but
			 * struggling with JPA and loading in order.
			 */
			((Blockchain) model.getAttribute("blockchain")).setChain(new_chain);
		} catch (Exception e) {
			System.out.println("CAN'T SORT IT FOR SOME REASON");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "see", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String seeIf(@ModelAttribute("blockchain") Blockchain blockchain, Model model)
			throws NoSuchAlgorithmException, PubNubException, InterruptedException {
//		pool = tService.getAllTransactionsAsTransactionPoolService(); // This is maybe expensive. Review for refactor
//		String transactionData = "MAIN INSTANCE STUBBED DATA"; // overruled -->>
//		transactionData = pool.getMinableTransactionDataString();
//		List<Transaction> tlist = tService.getAllTransactionsAsTransactionList();
//
//		System.err.println("TRANSACTION DATA Blockchain controller 84");
//		System.err.println("TRANSACTION DATA");
//		System.out.println(transactionData.toString());
//		System.err.println("TRANSACTION DATA");
//		System.err.println("TRANSACTION DATA");
//		Block new_block = blockchainApp.addBlockService("beancoin", transactionData);
////		pnapp.broadcastBlock(new_block); TURN BACK ON LATER
//		model.addAttribute("minedblock", new_block);
//		return new_block.webworthyJson(tlist); // This pollutes with escape chars \ because string
		Block b = ((Blockchain) model.getAttribute("blockchain")).getNthBlock(10);
		System.out.println(b.getData().getClass());
		List<TransactionRepr> tr = b.deserializeTransactionData();
		for (TransactionRepr r : tr) {
			System.out.println(r.getClass());
			System.out.println(r.getInput().get("address"));
//			System.out.println();
		}
		return b.getData().toString();
	}

}
