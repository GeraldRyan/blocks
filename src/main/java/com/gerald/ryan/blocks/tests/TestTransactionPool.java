package com.gerald.ryan.blocks.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.Map;

import org.junit.*;

import com.gerald.ryan.blocks.entity.Block;
import com.gerald.ryan.blocks.entity.Transaction;
import com.gerald.ryan.blocks.entity.TransactionPool;
import com.gerald.ryan.blocks.entity.Wallet;
import com.gerald.ryan.blocks.utilities.CryptoHash;

public class TestTransactionPool {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetTransaction()
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		TransactionPool tp = new TransactionPool();
		Transaction t = new Transaction(new Wallet(), "here", 165);
		tp.putTransaction(t);
		assertEquals(t, tp.getTransactionMap().get(t.getUuid()));
		Transaction t2 = new Transaction(new Wallet(), "here", 165);
		assertNotEquals(t, tp.getTransactionMap().get(t2.getUuid()));

	}

}
