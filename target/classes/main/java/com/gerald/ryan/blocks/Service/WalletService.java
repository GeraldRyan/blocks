package com.gerald.ryan.blocks.Service;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.gerald.ryan.blocks.Dao.UserDao;
import com.gerald.ryan.blocks.Dao.WalletDao;
import com.gerald.ryan.blocks.entity.User;
import com.gerald.ryan.blocks.entity.Wallet;

public class WalletService {
	WalletDao dao = new WalletDao();

	public Wallet addWalletService(Wallet w) {
		return dao.addWallet(w);
	}

	public Wallet getWalletService(String walletId) {
		return dao.getWallet(walletId);
	}

	/**
	 * 
	 * Updates balance of wallet by blockchain traversal
	 * 
	 * @param wallet
	 * @return
	 */
	public Wallet updateWalletBalanceService(Wallet wallet) {
		return dao.updateWallet(wallet);
	}

}
