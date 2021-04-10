package com.gerald.ryan.blocks.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.gerald.ryan.blocks.Service.UserService;
import com.google.gson.Gson;

@Entity
public class User {
	@Id
	String username;
	String password;
	String hint;
	String answer;
	String email;
	@OneToOne
	@JoinColumn(name = "walletId")
	Wallet wallet;
	@Embedded
	WalletForDB walletForDB;

	public User(String username, String password, String hint, String answer, String email, Wallet wallet) {
		super();
		this.username = username;
		this.password = password;
		this.hint = hint;
		this.answer = answer;
		this.email = email;
		this.walletForDB = new WalletForDB(wallet);

//		this.wallet = new Gson().toJson(wallet); // I think it will through an illegal serialization reflection error -
		// they don't want serializing private stuff like private key
	}

	public User(String username, String password, String hint, String answer, String email) {
		super();
		this.username = username;
		this.password = password;
		this.hint = hint;
		this.answer = answer;
		this.email = email;
		this.wallet = null;
	}

	public User() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public WalletForDB getWalletForDB() {
		return walletForDB;
	}

	public void setWalletForDB(WalletForDB walletForDB) {
		this.walletForDB = walletForDB;
	}

	public static void main(String[] args) {

	}

}
