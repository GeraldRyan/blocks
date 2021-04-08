package com.gerald.ryan.blocks.entity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import com.gerald.ryan.blocks.utilities.StringUtils;
import com.google.gson.Gson;

/**
 * An individual wallet for a miner. Keeps track of miner's balance. Allows
 * miner to authorize Transactions.
 * 
 * NOTE- HAVING A SETTER FOR ADDRESS FIELD WILL BREAK THIS BEAN at present
 * related to instantiating new transactions with request param page.
 * setAddress() breaks bean. Maybe setThisAddress will work fine but setAddress
 * will break the
 *
 * 
 * 
 * @author Gerald Ryan
 *
 */
@Embeddable
public class Wallet {
	public PrivateKey getPrivatekey() {
		return privatekey;
	}

	double balance;
	PrivateKey privatekey;
	PublicKey publickey;
	String address;

	static double STARTING_BALANCE = 1000;

	public Wallet() {
	}

	public Wallet(double balance, PrivateKey privatekey, PublicKey publickey, String address) {
		super();
		this.balance = balance;
		this.privatekey = privatekey;
		this.publickey = publickey;
		this.address = address;
	}

	/**
	 * used to recreate what's known of wallet without the private key info
	 * 
	 * @param balance
	 * @param publickey
	 * @param address
	 */
	public Wallet(double balance, PublicKey publickey, String address) {
		super();
		this.balance = balance;
		this.privatekey = privatekey;
		this.publickey = null;
		this.address = address;
	}

	public static Wallet createWallet()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		String address = String.valueOf(UUID.randomUUID()).substring(0, 8);
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "SunEC");
		keyGen.initialize(new ECGenParameterSpec("secp256k1"));
		KeyPair keyPair = keyGen.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		Wallet wallet = new Wallet(STARTING_BALANCE, privateKey, publicKey, address);
		System.out.println("NEW WALLET CREATED");
		return wallet;
	}

	/**
	 * Generate a signature based on data using local private key
	 * 
	 * @param data
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public byte[] sign(byte[] data)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
		Signature sig = Signature.getInstance("SHA256withECDSA", "SunEC");
		sig.initSign(privatekey);
		sig.update(data);
		byte[] signatureBytes = sig.sign();
		System.out.println("Data has been successfully signed");
		return signatureBytes;
	}

	// can sign wallet object
	public byte[] sign(Object dataObj) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
			SignatureException, IOException {
		byte[] data = StringUtils.objectToByteArray(dataObj);
		Signature sig = Signature.getInstance("SHA256withECDSA", "SunEC");
		sig.initSign(privatekey);
		sig.update(data);
		byte[] signatureBytes = sig.sign();
		System.out.println("Data has been successfully signed");
		return signatureBytes;
	}

	/**
	 * Verifies signature of data of given public key
	 * 
	 * @param signatureBytes
	 * @param data
	 * @param publickey
	 * @return
	 * @throws SignatureException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeyException
	 */
	public static boolean verifySignature(byte[] signatureBytes, byte[] data, PublicKey publickey)
			throws SignatureException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
		Signature sig = Signature.getInstance("SHA256withECDSA", "SunEC");
		sig.initVerify(publickey);
		sig.update(data);
		return sig.verify(signatureBytes);
	}

	public static boolean verifySignature(byte[] signatureBytes, String[] dataStr, PublicKey publickey)
			throws SignatureException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
			IOException {
		byte[] data = StringUtils.stringArrayToByteArray(dataStr);
		Signature sig = Signature.getInstance("SHA256withECDSA", "SunEC");
		sig.initVerify(publickey);
		sig.update(data);
		return sig.verify(signatureBytes);
	}

	public static boolean verifySignature(byte[] signatureBytes, Object obj, PublicKey publickey)
			throws SignatureException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException,
			IOException {
		byte[] data = StringUtils.objectToByteArray(obj);
		Signature sig = Signature.getInstance("SHA256withECDSA", "SunEC");
		sig.initVerify(publickey);
		sig.update(data);
		return sig.verify(signatureBytes);
	}

	public static void testSign() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException, SignatureException, InvalidKeyException {

		Wallet wallet1 = Wallet.createWallet();
		System.out.println(wallet1);
		System.out.println("_________________-");
		byte[] signatureBytes = wallet1.sign("CATSMEOW".getBytes("UTF-8"));
		System.out.println("Was it signed properly? Expect true. Drumroll... -> "
				+ wallet1.verifySignature(signatureBytes, "CATSMEOW".getBytes("UTF-8"), wallet1.getPublickey()));
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
		Wallet.testSign();
	}

	/**
	 * Restores a public key object in your chosen language from either a Base64
	 * String or a byte[] as received over the wire (contains X.509 standard)
	 * 
	 * @param pk
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey restorePK(byte[] pk)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("EC", "SunEC");
		PublicKey pkRestored = keyFactory.generatePublic(new X509EncodedKeySpec(pk, "EC"));
		return pkRestored;
	}

	public static PublicKey restorePK(String pk)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("EC", "SunEC");
		KeySpec ks = new X509EncodedKeySpec(pk.getBytes(StandardCharsets.UTF_8), "EC");
		PublicKey pkRestored = keyFactory.generatePublic(ks);
		return pkRestored;
	}

	public double getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return "Wallet [balance=" + balance + ", publickey=" + publickey + ", address=" + address + "]";
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAddress() {
		return address;
	}

	public static double getSTARTING_BALANCE() {
		return STARTING_BALANCE;
	}

	public static void setSTARTING_BALANCE(double STARTING_BALANCE) {
		STARTING_BALANCE = STARTING_BALANCE;
	}

	public PublicKey getPublickey() {
		return publickey;
	}

}
