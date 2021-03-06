package com.gerald.ryan.blocks.entity;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gerald.ryan.blocks.Service.BlockService;
import com.gerald.ryan.blocks.utilities.CryptoHash;
import com.gerald.ryan.blocks.utilities.TransactionRepr;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Gerald Ryan Block Class of blockchain app
 *
 *         Description: A block is a building block of a blockchain, and in the
 *         case of a cryptocurrency, contains as data a list of transactions.
 *         The block hash (here using SHA-256) is the result of the timestamp,
 *         the last_hash, the data, the difficulty and the nonce.
 *
 */
@Entity
@Table(name = "block")
public class Block {

	@Id
	long timestamp;
	protected String hash;
	protected String lastHash;
	@Lob
	@Column(columnDefinition = "LONGTEXT")
	String data;
	int difficulty;
	int nonce;
	@Transient
	static int GENESIS_DIFFICULTY = 10;
	static long GENESIS_TS = 1;
	@Transient
	static String GENESIS_HASH = "Genesis_Hash";
	@Transient
	static String GENESIS_LAST_HASH = "Genesis_Last_Hash";
	@Transient
	List<TransactionRepr> transactionList;

	public static final HashMap<String, Object> GENESIS_DATA = new HashMap<String, Object>();
	static {
		GENESIS_DATA.put("timestamp", Block.GENESIS_TS);
		GENESIS_DATA.put("last_hash", "genesis_last_hash");
		GENESIS_DATA.put("hash", "genesis_hash");
		GENESIS_DATA.put("data", "GENESIS GENESIS GENESIS");
		GENESIS_DATA.put("difficulty", 7);
		GENESIS_DATA.put("nonce", 1);
	}

	static long MILLISECONDS = 1;
	static long SECONDS = 1000 * MILLISECONDS;
	static long MINE_RATE = 2 * SECONDS;

	/**
	 * A block is a unit of storage for a blockchain that supports a cryptocurrency.
	 * 
	 * @param timestamp
	 * @param lastHash
	 * @param hash
	 * @param data
	 * @param difficulty
	 * @param nonce
	 */
	public Block(long timestamp, String lastHash, String hash, String data, int difficulty, int nonce) {
		super();
		this.timestamp = timestamp;
		this.lastHash = lastHash;
		this.hash = hash;
		this.data = data;
		this.difficulty = difficulty;
		this.nonce = nonce;
	}

	public Block() {
	}

	/**
	 * 
	 * Prints a header-conforming console adapted output string in the form
	 * 
	 * @return
	 */
	public String toStringConsole() {
		return "\n-----------BLOCK--------\ntimestamp: " + this.timestamp + "\nlastHash: " + this.lastHash + "\nhash: "
				+ this.hash + "\ndifficulty: " + this.getDifficulty() + "\nData: " + this.data + "\nNonce: "
				+ this.nonce + "\n-----------------------\n";
	}

	public String toStringWebAPI() {

		return String.format("timestamp: %s, lastHash:%s, hash:%s, data:[%s], difficulty:%s", timestamp, lastHash, hash,
				data, difficulty, nonce);
	}

	public String toStringFormatted() {
		String datastring = "";

		return String.format("%5s %10s %15s %15s %15s", timestamp, lastHash, hash, data, difficulty, nonce);
	}

	/**
	 * This utility serializer function helps deal with the complexities of Gson and
	 * escape characters for different types of object and list serialization.
	 * Understand its role by how it is used in this app.
	 * 
	 * @param tlist
	 * @return
	 */
	public <Transation> String webworthyJson(List<Transaction> tlist) {
		HashMap<String, Object> serializeThisBundle = new HashMap<String, Object>();
		List<TransactionRepr> treprlist = new ArrayList();
		for (Transaction t : tlist) {
			treprlist.add(new TransactionRepr(t));
		}
		serializeThisBundle.put("timestamp", timestamp);
		serializeThisBundle.put("hash", hash);
		serializeThisBundle.put("lasthash", lastHash);
		serializeThisBundle.put("difficulty", difficulty);
		serializeThisBundle.put("nonce", nonce);
		serializeThisBundle.put("data", treprlist);
		return new Gson().toJson(serializeThisBundle);
	}

	public <Transation> String webworthyJson(List<TransactionRepr> tlist, String foo) {
		HashMap<String, Object> serializeThisBundle = new HashMap<String, Object>();
		serializeThisBundle.put("timestamp", timestamp);
		serializeThisBundle.put("hash", hash);
		serializeThisBundle.put("lasthash", lastHash);
		serializeThisBundle.put("difficulty", difficulty);
		serializeThisBundle.put("nonce", nonce);
		serializeThisBundle.put("data", tlist);
		return new Gson().toJson(serializeThisBundle);
	}

	/**
	 * convert the block to a serialized JSON representation
	 * 
	 * @return
	 */
	public String toJSONtheBlock() {
		return new Gson().toJson(this);
	}

	public List<TransactionRepr> deserializeTransactionData() {
		List<TransactionRepr> lt = new ArrayList();
		java.lang.reflect.Type t = new TypeToken<List<TransactionRepr>>() {
		}.getType();
		List<TransactionRepr> listTR = null;
		System.out.println(this.getData());
		listTR = new Gson().fromJson(this.getData(), t);
		return listTR;
	}

	/**
	 * Deserialize a valid JSON string with GSON and convert it back into valid
	 * block
	 * 
	 * @param jsonel
	 * @return
	 */
	public static Block fromJsonToBlock(String jsonel) {
		Gson gson = new Gson();
		Block block_restored = gson.fromJson(jsonel, Block.class);
		return block_restored;
	}

	/**
	 * Mine a block based on given last block and data until a block hash is found
	 * that meets the leading 0's Proof of Work requirement.
	 * 
	 * @param last_block
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Block mine_block(Block last_block, String data) throws NoSuchAlgorithmException {
		long timestamp = new Date().getTime();
		String last_hash = last_block.getHash();
		int difficulty = Block.adjust_difficulty(last_block, timestamp);
		int nonce = 0;
		String hash = CryptoHash.getSHA256(timestamp, last_block.getHash(), data, difficulty, nonce);
		String proof_of_work = CryptoHash.n_len_string('0', difficulty);
		String binary_hash = CryptoHash.hex_to_binary(hash);
		String binary_hash_work_end = binary_hash.substring(0, difficulty);
		System.out.println("Difficulty: " + difficulty);
		System.out.println("Working");
		while (!proof_of_work.equalsIgnoreCase(binary_hash_work_end)) {
			nonce += 1;
			timestamp = new Date().getTime();
			difficulty = Block.adjust_difficulty(last_block, timestamp);
			hash = CryptoHash.getSHA256(timestamp, last_block.getHash(), data, difficulty, nonce);
			proof_of_work = CryptoHash.n_len_string('0', difficulty);
			binary_hash = CryptoHash.hex_to_binary(hash);
			binary_hash_work_end = binary_hash.substring(0, difficulty);
		}
		System.out.println("Solved at Difficulty: " + difficulty);
		System.out.println("Proof of work requirement " + proof_of_work);
		System.out.println("binary_Hash_work_end " + binary_hash_work_end);
		System.out.println("binary hash " + binary_hash);
		System.out.println("BLOCK MINED");
		return new Block(timestamp, last_hash, hash, data, difficulty, nonce);
	}

	/**
	 * Generates Genesis block with hard coded data that will be identical for all
	 * instances of blockchain
	 * 
	 * @return
	 */
	public static Block genesis_block() {
		long timestamp = 1;
		String last_hash = GENESIS_LAST_HASH;
		String hash = GENESIS_HASH;
		int difficulty = GENESIS_DIFFICULTY;
		int nonce = 0;
		return new Block((Long) GENESIS_DATA.get("timestamp"), (String) GENESIS_DATA.get("last_hash"),
				(String) GENESIS_DATA.get("hash"), (String) GENESIS_DATA.get("data"),
				(Integer) GENESIS_DATA.get("difficulty"), (Integer) GENESIS_DATA.get("nonce"));
	}

	/**
	 * Calculate the adjusted difficulty according to the MINE_RATE. Increase the
	 * difficulty for quickly mined blocks. Decrease the difficulty for slowly mined
	 * blocks.
	 * 
	 * @param last_block
	 * @param new_timestamp
	 */
	public static int adjust_difficulty(Block last_block, long new_timestamp) {
		long time_diff = new_timestamp - last_block.getTimestamp();

		if (time_diff < MINE_RATE) {

			return last_block.getDifficulty() + 1;
		} else if (last_block.getDifficulty() - 1 > 0) {
			return last_block.getDifficulty() - 1;
		} else {
			return 1;
		}
	}

	/**
	 * Validate block by enforcing following rules: - Block must have the proper
	 * last_hash reference - Block must meet the proof of work requirements -
	 * difficulty must only adjust by one - block hash must be a valid combination
	 * of block fields
	 * 
	 * @param last_block
	 * @param block
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean is_valid_block(Block last_block, Block block) throws NoSuchAlgorithmException {
		String binary_hash = CryptoHash.hex_to_binary(block.getHash());
		char[] pow_array = CryptoHash.n_len_array('0', block.getDifficulty());
		char[] binary_char_array = CryptoHash.string_to_charray(binary_hash);
		if (!block.getLastHash().equalsIgnoreCase(last_block.getHash())) {
			System.out.println("The last hash must be correct");
			return false;
			// Throw exception the last hash must be correct
		}
		if (!Arrays.equals(pow_array, Arrays.copyOfRange(binary_char_array, 0, block.getDifficulty()))) {
			System.out.println("Proof of work requirement not met");
			return false;
			// throw exception - proof of work requirement not met
		}
		if (Math.abs(last_block.difficulty - block.difficulty) > 1) {
			System.out.println("Block difficulty must adjust by one");
			return false;
			// throw exception: The block difficulty must only adjust by 1
		}
		String reconstructed_hash = CryptoHash.getSHA256(block.getTimestamp(), block.getLastHash(), block.getData(),
				block.getDifficulty(), block.getNonce());
		if (!block.getHash().equalsIgnoreCase(reconstructed_hash)) {
			System.out.println("The block hash must be correct");
			System.out.println(block.getHash());

			System.out.println(reconstructed_hash);
			return false;
			// throw exception: the block hash must be correct
		}
		return true;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getHash() {
		return hash;
	}

	public String getLastHash() {
		return lastHash;
	}

	public String getData() {
		return data;
	}

	public int getNonce() {
		return nonce;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + difficulty;
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((lastHash == null) ? 0 : lastHash.hashCode());
		result = prime * result + nonce;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (difficulty != other.difficulty)
			return false;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (lastHash == null) {
			if (other.lastHash != null)
				return false;
		} else if (!lastHash.equals(other.lastHash))
			return false;
		if (nonce != other.nonce)
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
//		String md = CryptoHash.getSHA256("foobar");
		Block genesis = genesis_block();
		System.out.println(genesis.toStringFormatted());
		genesis.setTimestamp(new Date().getTime());
		new BlockService().addBlockService(genesis);

	}

}
