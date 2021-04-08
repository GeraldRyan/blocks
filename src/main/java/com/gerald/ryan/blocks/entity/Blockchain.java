package com.gerald.ryan.blocks.entity;

//@GeneratedValue(strategy=GenerationType.AUTO)  Consider using this later under ID to auto increment
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eclipse.persistence.indirection.ValueHolderInterface;

import com.gerald.ryan.blocks.Service.BlockchainService;
import com.gerald.ryan.blocks.exceptions.BlocksInChainInvalidException;
import com.gerald.ryan.blocks.exceptions.ChainTooShortException;
import com.gerald.ryan.blocks.exceptions.GenesisBlockInvalidException;
import com.google.gson.Gson;



/**
 * 
 * @author Gerald Ryan Blockchain class of blockchain app. Blockchain class.
 *         Instantiate blockchain with a name as string
 *
 *
 */
@Entity
@Table(name = "blockchain")
public class Blockchain {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	@Column // (unique = true) doesn't need to be unique but why not
	String instance_name;
	long date_created;
	long date_last_modified;
	int length_of_chain;
	@OneToMany(targetEntity = Block.class, cascade = CascadeType.ALL, orphanRemoval=true)
//	@JoinTable(name = "blocks")
	List<Block> chain; // The chain itself

	/**
	 * Constructor function, initializes an ArrayList of Blocks with a valid genesis
	 * block. Future blocks are to be mined.
	 */
	public Blockchain(String name) {
		this.instance_name = name;
		this.date_created = new Date().getTime();
		this.chain = new ArrayList<Block>();
		this.chain.add(Block.genesis_block());
		this.length_of_chain = 1;
	}
	
	/**
	 * Zero arg constructor for java bean
	 */
	public Blockchain() {
//		this.chain = new ArrayList<Block>();
//		this.chain.add(Block.genesis_block());
	}

	public static Blockchain createBlockchainInstance(String name) {
		return new Blockchain(name);
	}

//	private ValueHolderInterface chainValueHolder;
//
//	// Use this get/set pair when configuring your Mapping
//	public void setChainValueHolder(ValueHolderInterface value) {
//		this.chainValueHolder = value;
//	}
//
//	public ValueHolderInterface getChainValueHolder() {
//		return this.chainValueHolder;
//	}

////	 Your application uses these methods to interact with Addresses
//	public void setChain(ArrayList<Block> chain) {
//		this.chainValueHolder.setValue(chain);
//	}
//
//	public ArrayList<Block> getChain() {
//		return (ArrayList<Block>) this.chainValueHolder.getValue();
//	}

	/**
	 * Adds block to blockchain by calling block class's static mine_block method.
	 * This ensures block is valid in itself, and is attached to end of local chain, ensuring chain is valid.
	 * 
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public Block add_block(String[] data) throws NoSuchAlgorithmException {
		Block new_block = Block.mine_block(this.chain.get(this.chain.size() - 1), data);
		this.chain.add(new_block);
		this.length_of_chain++;
		this.date_last_modified = new Date().getTime();
		return new_block;
	}

	/**
	 * Adds block to blockchain by calling block class's static mine_block method.
	 * This ensures block is valid in itself, and is attached to end of local chain, ensuring chain is valid.
	 * 
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public Block add_block(String dataScalar) throws NoSuchAlgorithmException {
		Block new_block = Block.mine_block(this.chain.get(this.chain.size() - 1), dataScalar);
		this.chain.add(new_block);
		this.length_of_chain++;
		this.date_last_modified = new Date().getTime();
		return new_block;
	}

	/**
	 * Replace the local chain with the incoming chain if the following apply: - the
	 * incoming chain is longer than the local one - the incoming chain is formatted
	 * properly
	 * 
	 * @param chain
	 * @throws NoSuchAlgorithmException
	 * @throws ChainTooShortException
	 * @throws BlocksInChainInvalidException
	 * @throws GenesisBlockInvalidException
	 */
	public void replace_chain(Blockchain other_blockchain) throws NoSuchAlgorithmException, ChainTooShortException,
			GenesisBlockInvalidException, BlocksInChainInvalidException {
		// TODO how will I implement this? Different localhosts will have different
		// chains?
		if (other_blockchain.chain.size() <= this.chain.size()) {
			throw new ChainTooShortException("Chain too short to replace");
		}
		if (!Blockchain.is_valid_chain(other_blockchain)) {
			System.out.println("Cannot replace chain. The incoming chain is invalid");
			return;
		}

		try {
			Blockchain.is_valid_chain(other_blockchain);
			this.chain = other_blockchain.chain;
			this.length_of_chain = other_blockchain.length_of_chain;
			this.date_last_modified = new Date().getTime();
			System.out.println("Chain replaced with valid longer chain");
		} catch (GenesisBlockInvalidException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 
	 * Sorts the arraylist by timestamp, putting it (back) in the order it should be in and naturally is in,
	 * but sometimes network requets or database pulls with JPA can break the order. This fixes humpty dumpty where necessary.
	 * 
	 * As such, this is not an ideal method. It's a fixer method that shouldn't be needed, though if code gets fixed
	 * Might still be worth keeping around in the toolbelt. 
	 * 
	 * If ArrayList<Block> is not in order, it's not a real blockchain, and even though you have the set of blocks in the chain
	 * with enough info to make it recreatable, in the current form, replace_chain won't work
	 */
//	public static void sort_arrayList(Blockchain blockchain) {
//		blockchain.getChain().sort((b1, b2) -> (int)(b1.getTimestamp() - b2.getTimestamp()));
////		blockchain.getChain().get(0).getTimestamp()
//	}
//	
//	public int compareTimestamps() {
//		
//	}

	/**
	 * Replace the local chain with the incoming chain if the following apply: - the
	 * incoming chain is longer than the local one - the incoming chain is formatted
	 * properly
	 * 
	 * @param chain
	 * @throws NoSuchAlgorithmException
	 * @throws ChainTooShortException
	 * @throws BlocksInChainInvalidException
	 * @throws GenesisBlockInvalidException
	 */
	public void replace_chain(List<Block> other_chain) throws NoSuchAlgorithmException, ChainTooShortException,
			GenesisBlockInvalidException, BlocksInChainInvalidException {
		// TODO how will I implement this? Different localhosts will have different
		// chains?
		System.out.println(other_chain.size() + " " + this.chain.size());
		if (other_chain.size() <= this.chain.size()) {
			throw new ChainTooShortException("Chain too short to replace");
		}
		if (!Blockchain.is_valid_chain(other_chain)) {
			System.out.println("Cannot replace chain. The incoming chain is invalid");
			return;
		}

		try {
			Blockchain.is_valid_chain(other_chain);
			this.chain = other_chain;
			this.length_of_chain = other_chain.size();
			this.date_last_modified = new Date().getTime();
			System.out.println("Chain replaced with valid longer chain");
		} catch (GenesisBlockInvalidException e) {
			System.out.println(e);
		} catch (BlocksInChainInvalidException e) {
			System.out.println(e);
		}

//		else {
//			try {
//				Blockchain.is_valid_chain(other_blockchain);
//			} catch (Exception e) {
//				throw new // exception
//				// block of code to handle errors
//			}
//		}
	}

	/** 
	 * This is a checker method that checks whether the chain will replace, in case it is required for lifecycle actions
	 * In practice, this is used for flushing the databse's join table for JPA in the @OneToMany object due to fact no way was yet found
	 * to update the chain by strict replacement [errors such as duplicate key are found if not flushed]
	 * @param other_chain
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws GenesisBlockInvalidException
	 * @throws BlocksInChainInvalidException
	 */
	public boolean willReplace(List<Block> other_chain) throws NoSuchAlgorithmException, GenesisBlockInvalidException, BlocksInChainInvalidException {
		System.out.println(other_chain.size() + " " + this.chain.size());
		if (other_chain.size() <= this.chain.size()) {
			return false;
		}
		if (!Blockchain.is_valid_chain(other_chain)) {
			return false;
		}
		return true;
	}

	/**
	 * Setter method for chain of blockchain instance.
	 * Probably should not exist. Probably a major blockchain security breech and definitely should not be necessary, but..
	 * has been necessary for project for refreshing instance of blockchain in memory in Spring MVC controller for display in page.
	 * If new way is found to sync, can maybe refactor this out. 
	 */
	public void setChain(List<Block> chain) {
		this.chain = chain;
	}

	/**
	 * Validate the incoming chain. Enforce the following rules: - the chain must
	 * start with the genesis block - blocks must be formatted correctly
	 * 
	 * @param blockchain
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws GenesisBlockInvalidException
	 * @throws BlocksInChainInvalidException
	 */
	public static boolean is_valid_chain(Blockchain blockchain)
			throws NoSuchAlgorithmException, GenesisBlockInvalidException, BlocksInChainInvalidException {
		if (!blockchain.chain.get(0).equals(Block.genesis_block())) {
			System.out.println("The genesis block must be valid");
			throw new GenesisBlockInvalidException("Genesis Block is invalid");
		}
		for (int i = 1; i < blockchain.chain.size(); i++) {
			Block current_block = blockchain.chain.get(i);
			Block last_block = blockchain.chain.get(i - 1);
			if (!Block.is_valid_block(last_block, current_block)) {
//				System.out.println("At least one of the blocks in the chain is not valid");
				throw new BlocksInChainInvalidException("At least one of the blocks in the chain is not valid");
			}
		}
		return true;
	}


	/**
	 * Validate the incoming chain. Enforce the following rules: - the chain must
	 * start with the genesis block - blocks must be formatted correctly
	 * 
	 * @param blockchain
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws GenesisBlockInvalidException
	 * @throws BlocksInChainInvalidException
	 */
	public static boolean is_valid_chain(List<Block> other_chain)
			throws NoSuchAlgorithmException, GenesisBlockInvalidException, BlocksInChainInvalidException {
		if (!other_chain.get(0).equals(Block.genesis_block())) {
			System.out.println("The genesis block must be valid");
			throw new GenesisBlockInvalidException("Genesis Block is invalid");
		}
		for (int i = 1; i < other_chain.size(); i++) {
			Block current_block = other_chain.get(i);
			Block last_block = other_chain.get(i - 1);
			if (!Block.is_valid_block(last_block, current_block)) {
//				System.out.println("At least one of the blocks in the chain is not valid");
				throw new BlocksInChainInvalidException("At least one of the blocks in the chain is not valid");
			}
		}
		return true;
	}

	/**
	 * returns headerless console customized string output
	 * @return
	 */
	public String toStringConsole() {
		return String.format("%5s %15s %15s %15s %15s", id, instance_name, date_created, date_last_modified,
				length_of_chain, "length", "content");
	}

	
	/**
	 * Returns blockchain's metadata information as a string
	 * @return
	 */
	public String toStringMeta() {
		return String.format(
				"Blockchain Metadata: id: %s instance: %s date_created: %s date_modified: %s length of chain: %s", id,
				instance_name, date_created, date_last_modified, length_of_chain);
	}

	public String toStringBroadcastChain() {
		String string_to_return = "";
		for (Block b : chain) {
			string_to_return += b.toStringWebAPI();
		}
		return string_to_return;
	}

	/**
	 * Uses GSON library to serialize blockchain chain as json string.
	 */
	public String toJSONtheChain() {
//		return "[{\"name\": \"foo\", \"amount\": 4}]";
		return new Gson().toJson(chain);
	}

	public ArrayList fromJSONtheChain(String json) {
		return new Gson().fromJson(json, ArrayList.class);
	}

	/**
	 * Helper method for getting last block (peeking)
	 */
	public Block getLastBlock() {
		return this.getChain().get(getLength_of_chain() - 1);
	}

	public int getId() {
		return id;
	}

	public String getInstance_name() {
		return instance_name;
	}

	public long getDate_created() {
		return date_created;
	}

	public long getDate_last_modified() {
		return date_last_modified;
	}

	public int getLength_of_chain() {
		return length_of_chain;
	}

	public List<Block> getChain() {
		return chain;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
//		Blockchain blockchain = new BlockchainService().newBlockchainService("beancoin");
//		System.out.println(blockchain.fromJSONtheChain(blockchain.toJSONtheChain()));
//
//		new BlockchainService().addBlockService("beancoin", new String[] { "Shakespeare", "wrote", "it" });
//		
////		blockchain.add_block(new String[] { "Shakespeare", "wrote", "it" });
//		System.out.println(blockchain);
	}

}
