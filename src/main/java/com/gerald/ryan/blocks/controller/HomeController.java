package com.gerald.ryan.blocks.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

//select instance_name,b.id,hash,data from blockchain c inner join blocksbychain bc on c.id=bc.blockchain_id inner join block b on bc.chain_id=b.id;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.gerald.ryan.blocks.Service.UserService;
import com.gerald.ryan.blocks.Service.WalletService;
import com.gerald.ryan.blocks.entity.Login;
import com.gerald.ryan.blocks.entity.Message;
import com.gerald.ryan.blocks.entity.Transaction;
import com.gerald.ryan.blocks.entity.TransactionPool;
import com.gerald.ryan.blocks.entity.User;
import com.gerald.ryan.blocks.entity.Wallet;
import com.gerald.ryan.blocks.pubsub.PubNubApp;
import com.google.gson.Gson;
import com.pubnub.api.PubNubException;


/*
 * Key Data Model Session attributes:
 * On register -- should be logged in, hence everything that login has plus ?nothing else?
 * On Login(success) -- Session: wallet, username, isloggedin=true, failed=false
 * On Login(fail) -- Session: failed=true, msg="various string"
 * On logout -- Session: wallet=null, username=null, isloggedin=false
 * 
 */

//@RequestMapping("/admin")
@Controller
@SessionAttributes({ "blockchain", "wallet", "username", "isloggedin", "user", "msg", "transactionpool" })
public class HomeController {

	// This is not Inversion of Control! This is tight coupling
	PubNubApp pnapp = new PubNubApp();
	UserService userService = new UserService();

	public HomeController() throws InterruptedException {
	}

	@ModelAttribute("isloggedin")
	public boolean isLoggedIn() {
		return false;
	}

	@ModelAttribute("randomnumber")
	public String randomUUID() {
		return String.valueOf(UUID.randomUUID()).substring(0, 8);
	}

	@ModelAttribute("transactionpool")
	public TransactionPool initTransactionPool() {
		return new TransactionPool();
	}

//	@ModelAttribute("pubnubapp")
//	public PubNubApp addPubNub() throws InterruptedException {
//		return new PubNubApp();
//	}

	@Development // does nothing just a markup annotation
	public void consoleModelProperties(Model model) {
		System.out.println("Model class is " + model.getClass());
		System.out.println(model.toString());

	}

	@GetMapping("")
	public String showIndex(Model model) {
		consoleModelProperties(model);
		((TransactionPool) model.getAttribute("transactionpool")).consoleLogAll();

		return "index";
	}

	@GetMapping("/login")
	public String showLoginPage(@ModelAttribute("login") Login login) {
		return "login/login";
	}

	@PostMapping("/login")
	public String processLogin(Model model, @ModelAttribute("login") Login login, HttpServletRequest request,
			HttpServletResponse response) {
		String result = validateUserAndPassword(login.getUsername(), login.getPassword());
		if (result == "true") {
			model.addAttribute("username", login.getUsername());
			model.addAttribute("isloggedin", true);
			model.addAttribute("user", new UserService().getUserService(login.getUsername()));
			model.addAttribute("failed", false);
			model.addAttribute("wallet", new WalletService().getWalletService(login.getUsername()));
		} else if (result == "user not found") {
			System.out.println("User not found in records");
			model.addAttribute("failed", true);
			model.addAttribute("msg", "User not found. Please try again");
		} else {
			System.err.println("Password not correct");
			model.addAttribute("failed", true);
			model.addAttribute("msg", "Password incorrect. Please try again");
		}
		return "index";
	}

	@GetMapping("/logout")
	public String logOut(Model model, HttpServletRequest request) {
		model.addAttribute("isloggedin", false);
		model.addAttribute("wallet", null);
		model.addAttribute("username", null);
		HttpSession httpSession = request.getSession();
		return "redirect:/";
	}

	@GetMapping("/publish")
	public String getPublish(Model model) {
		model.addAttribute("message", new Message());
		return "publish";
	}

	@RequestMapping(value = "homeplay", method = RequestMethod.GET)
	public String getPlay(Model model) {
//		StringUtils.mapKeyValue(model.asMap(), "homecontroller 302");
		System.out.println("Wallet Address " + ((Wallet) model.getAttribute("wallet")).getAddress());
		System.out.println("random number" + ((String) model.getAttribute("randomnumber")));
		return "play";
	}

//	String messages;

	@PostMapping("/publish")
	public String getPublish(@ModelAttribute("message") Message message, Model model)
			throws InterruptedException, PubNubException {

		System.out.println("Publish post mapping ran");
//		messages += message.getMessage() + "\n";
		pnapp.publish(message.getChannel(), message.getMessage());
		model.addAttribute("display", message.getMessage());
//		model.addAttribute("display", messages);
		return "publish";
	}

	@GetMapping("/subscribe")
	public String getSubscribe() {
		return "subscribe";
	}

	@PostMapping("/subscribe")
	public String subToChannel(@RequestParam("channel") String channel) throws InterruptedException, PubNubException {
		pnapp.subscribe(channel);
		System.out.println("Publish post mapping ran");
//		messages += message.getMessage() + "\n";

//		model.addAttribute("display", messages);
		return "subscribe";
	}

	@GetMapping("/transactionpool")
	public String getTransactionPool(Model model) {
		List<Transaction> transactionList = new TransactionService().getAllTransactionsAsTransactionList();
		model.addAttribute("transactionpoollist", transactionList);
		return "transactionpool";
	}

	@PostMapping("/transactionpool")
	@ResponseBody
	public String postTransactionPool(Model model) {

		TransactionPool pool = new TransactionService().getAllTransactionsAsTransactionPoolService();
		if (pool.getMinableTransactionDataString() == null) {
			return "No transactions in the pool. Tell your friends to make transactions";
		}
		String transactionData = pool.getMinableTransactionDataString();
		return pool.getMinableTransactionDataString();
	}

	public String validateUserAndPassword(String username, String password) {
		User user = userService.getUserService(username);

		if (user == null) {
			return "user not found";
		}
		if (user.getPassword().equalsIgnoreCase(password)) {
			return "true";
		}
		return "false";
	}

	public static void main(String[] args)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		new UserService().addUserService(new User("zelda", "ganon", "powerwisdom", "love", "zelda@hyrule.hr"));
	}
}
