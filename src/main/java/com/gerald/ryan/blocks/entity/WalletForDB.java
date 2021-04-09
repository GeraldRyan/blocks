package com.gerald.ryan.blocks.entity;

import javax.persistence.Embeddable;
// Wallet strategy is crap. Change this completely before presentation to change price
@Embeddable
public class WalletForDB {

	byte[] publickey;
	byte[] privatekey;
	double balance;
	String address;

	public WalletForDB(Wallet wallet) {
		super();
		this.publickey = wallet.getPublickey().getEncoded();
		this.privatekey = wallet.getPrivatekey().getEncoded();
		this.address = wallet.getAddress();
		this.balance = wallet.getBalance();
	}

	public WalletForDB() {

	}

}
