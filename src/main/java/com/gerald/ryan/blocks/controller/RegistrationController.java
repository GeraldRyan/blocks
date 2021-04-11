package com.gerald.ryan.blocks.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gerald.ryan.blocks.Service.UserService;
import com.gerald.ryan.blocks.Service.WalletService;
import com.gerald.ryan.blocks.entity.User;
import com.gerald.ryan.blocks.entity.Wallet;

@Controller
@RequestMapping("/register")
@SessionAttributes({ "user", "isloggedin", "wallet", "username" })
public class RegistrationController {
	UserService userService = new UserService();

	@GetMapping("")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new User());
		return "registration/register";
	}

	@PostMapping("")
	public String registerUser(Model model, @ModelAttribute("user") User user)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		System.out.println(user.toString());
		System.out.println(user.getEmail());
		System.out.println(user.getHint());
		Wallet wallet = Wallet.createWallet(user.getUsername());
		new UserService().addUserService(user);
		new WalletService().addWalletService(wallet);
		model.addAttribute("isloggedin", true);
		model.addAttribute("user", user);
		model.addAttribute("wallet", wallet);

		return "registration/welcomepage";
	}

	@GetMapping("welcome")
	public String getWelcome(Model model) {
		User u = ((User) model.getAttribute("user"));
		return "registration/welcomepage";
	}

}
