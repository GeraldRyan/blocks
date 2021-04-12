package com.gerald.ryan.blocks.exceptions;

public class TransactionAmountExceedsBalance extends Exception {
	public TransactionAmountExceedsBalance(String errorMessage) {
		super(errorMessage);
	}
}
