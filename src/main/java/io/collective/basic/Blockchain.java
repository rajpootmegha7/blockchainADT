package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain;
    public Blockchain(){
        this.chain = new ArrayList<>();
    }

    public boolean isEmpty() {
        return chain.isEmpty();
    }

    public void add(Block block) {
        chain.add(block);
    }

    public int size() {
        return chain.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {
        if (chain.isEmpty()) {
            return true;
        }
        // todo - check mined

        // todo - check previous hash matches

        // todo - check hash is correctly calculated

        for(int i =1; i<chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Check if the hash of the current block matches its calculated hash
            if (!currentBlock.getHash().equals(currentBlock.calculatedHash())) {
                return false;
            }

            // Check if the previous hash of the current block matches the hash of the previous block
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            // Check if the block is mined (starts with "00")
            if (!isMined(currentBlock)) {
                return false;
            }
        }
            // Validate the genesis block
            Block genesisBlock = chain.get(0);
            return isMined(genesisBlock) && genesisBlock.getHash().equals(genesisBlock.calculatedHash());

        }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        int nonce = 0;
        Block mined ;
        do {
            mined= new Block(block.getPreviousHash(), block.getTimestamp(), nonce++);
        }
        while (!isMined(mined));
            return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}