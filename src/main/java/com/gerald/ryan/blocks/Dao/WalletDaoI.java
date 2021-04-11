package com.gerald.ryan.blocks.Dao;

import java.util.List;

import com.gerald.ryan.blocks.entity.User;
import com.gerald.ryan.blocks.entity.Wallet;

public interface WalletDaoI {
	public Wallet addWallet(Wallet w);

	public Wallet getWallet(String walletId);

	public Wallet updateWallet(Wallet wallet);

	public Wallet removeWallet(String walletId);
}
