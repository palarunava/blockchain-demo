package com.cognizant.blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.cognizant.blockchain.model.Block;
import com.cognizant.blockchain.util.Utility;

public class NoobChain {
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;

	public static void main(String[] args) {
		// add our blocks to the blockchain ArrayList:
		Date start = new Date();
		System.out.println("Trying to Mine block 1... ");
		addBlock(new Block("Hi im the first block", "0"), true);

		System.out.println("Trying to Mine block 2... ");
		addBlock(new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).getHash()), true);

		System.out.println("Trying to Mine block 3... ");
		addBlock(new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).getHash()), true);

		System.out.println("\nBlockchain is Valid: " + isChainValid());

		String blockchainJson = Utility.getJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
		Date end = new Date();
		long diffInMillies = Math.abs(end.getTime() - start.getTime());
		long timeElapsed = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		System.out.println("Time taken = " + timeElapsed + " seconds");
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		// loop through blockchain to check hashes:
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// compare registered hash and calculated hash:
			if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			// compare previous hash and registered previous hash
			if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			// check if hash is solved
			if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}

		}
		return true;
	}

	public static void addBlock(Block newBlock, boolean mine) {
		if (mine) {
			newBlock.mineBlock(difficulty);
		}
		blockchain.add(newBlock);
	}
}
