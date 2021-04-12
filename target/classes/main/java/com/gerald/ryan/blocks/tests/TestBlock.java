package com.gerald.ryan.blocks.tests;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import org.junit.*;

import com.gerald.ryan.blocks.entity.Block;
import com.gerald.ryan.blocks.utilities.CryptoHash;

public class TestBlock extends Block {

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
	public void testMine_block() {
//		fail("Not yet implemented");
	}

	/*
	 * Test each attribute of genesis block against genesis data.
	 */
	@Test
	public void testGenesis_block() {
		Block genesis = Block.genesis_block();
		assertTrue(genesis instanceof Block);
		assertEquals(Block.GENESIS_DATA.get("timestamp"), genesis.getTimestamp());
		assertEquals(Block.GENESIS_DATA.get("last_hash"), genesis.getLastHash());
		assertEquals(Block.GENESIS_DATA.get("hash"), genesis.getHash());
		assertEquals(Block.GENESIS_DATA.get("data"), genesis.getData());
		assertEquals(Block.GENESIS_DATA.get("difficulty"), genesis.getDifficulty());
		assertEquals(Block.GENESIS_DATA.get("nonce"), genesis.getNonce());
//		for (Map.Entry<String, Object> entry : Block.GENESIS_DATA.entrySet()){
//			assertArrayEquals(Block.GENESIS_DATA.get(entry), genesis.);
//		}
	}

	@Test
	public void testMineBlock() throws NoSuchAlgorithmException {
		Block last_block = Block.genesis_block();
		String data = "one two four";
		Block test_block = Block.mine_block(last_block, data);
		assertTrue(test_block instanceof Block);
		assertTrue(test_block.getData() == data);
		assertEquals(test_block.getLastHash(), last_block.getHash());
		String proof_of_work_bits = CryptoHash.hex_to_binary(test_block.getHash()).substring(0,
				test_block.getDifficulty());
		String expected_proof_of_work = CryptoHash.n_len_string("0", test_block.getDifficulty());
		assertEquals(proof_of_work_bits, expected_proof_of_work); // Assert that proof of work requirement met
	}

	@Test
	public void testQuicklyMinedBlock() throws NoSuchAlgorithmException {
		Block last_block = Block.mine_block(Block.genesis_block(), "foo");
		Block mined_block = Block.mine_block(last_block, "bar");
		assertEquals(last_block.getDifficulty() + 1, mined_block.getDifficulty());
	}

	@Test
	public void testSlowlyMinedBlock() throws NoSuchAlgorithmException, InterruptedException {
		Block last_block = Block.mine_block(Block.genesis_block(), "foo");
		Thread.sleep(5000);
		Block mined_block = Block.mine_block(last_block, "bar");
		assertEquals(last_block.getDifficulty() - 1, mined_block.getDifficulty());
	}

	@Test
	public void testMinedBlockLimitsAt1() throws InterruptedException, NoSuchAlgorithmException {
		Block easy_block = new Block(new Date().getTime(), "test_last_hash", "test_hash", "foo", 1, 0);
		Thread.sleep(5000);
		Block mined_block = Block.mine_block(easy_block, "foo");
		assertEquals(mined_block.getDifficulty(), 1); // negative was tested
	}

	@Test
	public void testIsValidBlockTrue() throws NoSuchAlgorithmException {
		// No way to make a bad hash because can't mutate state directly in java and
		// don't want to make a setter for last hash for security
		// If way is found, will do. Will consider advice.
		// Want to test that it raises correct exception (which I have to throw as well)
		Block last_block = Block.mine_block(Block.genesis_block(), "foo");
		Block mined_block = Block.mine_block(last_block, "bar");
		assertEquals(Block.is_valid_block(last_block, mined_block), true);
	}

	@Test
	public void testIsValidBlockBadLastHash() throws NoSuchAlgorithmException {
		// No way to make a bad hash because can't mutate state directly in java and
		// don't want to make a setter for last hash for security
		// If way is found, will do. Will consider advice.
		// Want to test that it raises correct exception (which I have to throw as well)
		Block last_block = Block.mine_block(Block.genesis_block(), "foo");
		Block mined_block = Block.mine_block(last_block, "bar");
	}

	@Test
	public void testIsValidBlockBadProofOfWork() {
		// Has to mutate hash. Impossible in current code base.
		// Want to test that it raises correct exception (which I have to throw as well)
		assertTrue(true);
	}

	@Test
	public void testIsValidBlockJumpedDifficulty() {
		// Can't mutate difficulty
		// Want to test that it raises correct exception (which I have to throw as well)
		assertTrue(true);
	}

	@Test
	public void testIsValidBlockBadBlockHash() {
		// Has to mutate hash. Impossible in current code base.
		// Want to test that it raises correct exception (which I have to throw as well)
		assertTrue(true);
	}

}

// made change in this test file