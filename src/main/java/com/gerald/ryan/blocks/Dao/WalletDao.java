package com.gerald.ryan.blocks.Dao;

import java.util.NoSuchElementException;

import com.gerald.ryan.blocks.Service.BlockchainService;
import com.gerald.ryan.blocks.dbConnection.DBConnection;
import com.gerald.ryan.blocks.entity.User;
import com.gerald.ryan.blocks.entity.Wallet;
import com.gerald.ryan.blocks.entity.WalletForDB;

public class WalletDao extends DBConnection implements WalletDaoI {

	@Override
	public Wallet addWallet(Wallet w) {
		this.connect();
		em.getTransaction().begin();
		em.persist(w);
		em.getTransaction().commit();
		this.disconnect();
		return w;
	}

	@Override
	public Wallet getWallet(String walletId) {
		this.connect();
		Wallet w = em.find(Wallet.class, walletId);
		this.disconnect();
		return w;
	}

	@Override
	public Wallet removeWallet(String walletId) {

		return null;
	}

	@Override
	public Wallet updateWallet(Wallet wallet) {
		this.connect();
		em.getTransaction().begin();
		double newBalance = Wallet.calculateBalance(new BlockchainService().getBlockchainService("beancoin"),
				wallet.getAddress());
		wallet.setBalance(newBalance);
		em.getTransaction().commit();
		this.disconnect();
		return wallet;
	}

}
