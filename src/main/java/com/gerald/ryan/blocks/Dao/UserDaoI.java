package com.gerald.ryan.blocks.Dao;

import java.util.List;

import com.gerald.ryan.blocks.entity.User;
import com.gerald.ryan.blocks.entity.Wallet;



public interface UserDaoI {
	public User addUser(User user);

	public User getUser(String username);

	public Wallet addWallet(String username, Wallet wallet);

//	public User updateUser(User user);
	public User removeUser(String username);

	public boolean authenticateUser(String username, String password);

//	public List<User> getAllUsers(); // is this safe? It also gets their wallets

}
